use strict;
use warnings;
use IO::File;
use Text::CSV;
use XML::Writer;
use IO::File;
use Getopt::Long;
use LWP::Simple;

my $ALM_xml_file = "./ALM.xml";
my $jenkins_server = 'awsjenkins.techops.ventyx.abb.com';
my $automated_field;
my $options;

sub main() {
   # Validate the command line options.
   $options = check_params();   
   
   my $jenkins_job = $options->{'sel_reg_job'};
   $automated_field = $options->{'automated_field'};
   
   my $build_url = "http://$jenkins_server:8080/job/$jenkins_job/";
   
   my %ALM_ids_current;
   my %ALM_ids_previous;
   
   my $last_build = $options->{'latest_build'};
   my $ALM_csv_current_url = "$build_url/$last_build/artifact/AxisP2PAutomation/AxisP2PAutomation/ALM.csv";
   my $ALM_csv_current = "./ALM.csv";
   my $status = getstore($ALM_csv_current_url, $ALM_csv_current);
   if ( is_success($status) )
   {
     print "$ALM_csv_current_url file downloaded correctly\n";
   }
   else
   {
     print "error downloading file: $status\n";
   } 
   
   read_ALM_csv($ALM_csv_current, \%ALM_ids_current);
   
   if ($last_build > 0) {
      my $previous_build = $last_build - 1;
      my $ALM_csv_previous_url = "$build_url/$previous_build/artifact/AxisP2PAutomation/AxisP2PAutomation/ALM.csv";
      my $ALM_csv_previous = "./ALM_previous.csv";
      my $status2 = getstore($ALM_csv_previous_url, $ALM_csv_previous);
      if ( is_success($status2) )
      {
        print "$ALM_csv_previous_url file downloaded correctly\n";
      }
      else
      {
        print "error downloading file: $status2\n";
      } 
      if ( -f $ALM_csv_previous ) {
         read_ALM_csv($ALM_csv_previous, \%ALM_ids_previous); 
      }  
   }
   
   write_ALM_diff_xml(\%ALM_ids_current, \%ALM_ids_previous);

}

sub read_ALM_csv() {
   my ($filename, $id_hash) = @_;
   undef %$id_hash;
   my $csv = Text::CSV->new ( { binary => 1 } )  # should set binary attribute.
                 or die "Cannot use CSV: ".Text::CSV->error_diag ();
 
   open my $fh, "<:encoding(utf8)", $filename or die "$filename: $!";
   
   while ( my $row = $csv->getline( $fh ) ) {
      my $alm_id = $row->[0];
      if (defined($alm_id) && $alm_id ne "") {
         $$id_hash{$alm_id}++;
      }
   }
   $csv->eof or $csv->error_diag();
   close $fh; 
   
}

sub write_ALM_diff_xml() {
   my($ids_current, $ids_previous) = @_;
   
   my %ids_deleted;
   
   for my $id (keys %$ids_previous)
   {
      if ( !(exists $ids_current->{$id}) ) {
         $ids_deleted{$id}++;
      } 
   }

   my $output = IO::File->new("> $ALM_xml_file");

   my $writer = XML::Writer->new(OUTPUT => $output);

   $writer->startTag('ALMItemFieldValues');

   for my $key (keys %$ids_current)
   {
      $writer->startTag('ALMItemFieldValue');
      $writer->dataElement('ItemType', 'Test');
      $writer->dataElement('ItemId', $key);
      $writer->dataElement('FieldName', $automated_field);
      $writer->dataElement('FieldValue', 'Y');
      $writer->endTag('ALMItemFieldValue');                     
   }   
   
   for my $key (keys %ids_deleted)
   {
      $writer->startTag('ALMItemFieldValue');
      $writer->dataElement('ItemType', 'Test');
      $writer->dataElement('ItemId', $key);
      $writer->dataElement('FieldName', $automated_field);
      $writer->dataElement('FieldValue', 'N');
      $writer->endTag('ALMItemFieldValue');                     
   }
   $writer->endTag('ALMItemFieldValues');

   $writer->end();
   $output->close();
}

sub check_params() {
   my (%options, $file_list_ref);
   if (!GetOptions( \%options,
         ('sel_reg_job=s', 'latest_build=s', 
         'automated_field=s')) ) {
      exec( "pod2usage $0" );
   }
 
   if (! defined $options{'sel_reg_job'}) {
      exit_err("sel_reg_job must be specified");
   }

   return(\%options);
}

sub exit_err {
   my (@msgs) = @_;
   print STDERR 'ERROR: ' . join("\n", @msgs) . "\n";
   exit(1);
}



#------------------------------------------------------------------------------
main();
exit;