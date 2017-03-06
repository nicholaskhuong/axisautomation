<?xml version="1.0" encoding="UTF-8"?>
<!--
    @Description: Stylesheet to be used with Ant JUnitReport output. 
                  It creates a set of javascript data for Ellipse JUnit report.
    @Author: TRAN Q. Cuong (Connor)
    @Last update: 04 Jul 2013
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
    version="1.0" 
    xmlns:exslt="http://exslt.org/common"     
    xmlns:f="xalan://com.example.Functions"
    xmlns:saxon="http://saxon.sf.net"
    xmlns:lxslt="http://xml.apache.org/xslt"
    xmlns:java-uri="java:java.net.URI"
    xmlns:java-file="java:java.io.File"
    xmlns:java-fis="java:java.io.FileInputStream"
    xmlns:java-fos="java:java.io.FileOutputStream"
    xmlns:java-fc="java.nio.channels.FileChannel"
    xmlns:redirect="http://xml.apache.org/xalan/redirect" 
    xmlns:stringutils="xalan://org.apache.tools.ant.util.StringUtils" 
    xmlns:seconds="xalan://org.apache.xalan.internal.lib.ExsltDatetime.seconds"
    xmlns:date="http://exslt.org/dates-and-times"
    extension-element-prefixes="redirect date saxon exsl">
    <xsl:output method="html" indent="yes" encoding="UTF-8" omit-xml-declaration="yes" cdata-section-elements="text" />
    <xsl:decimal-format decimal-separator="." grouping-separator="," />
    <xsl:strip-space elements="*" />
	
	<xsl:template match="/">
        <xsl:text disable-output-escaping="yes">
        &lt;j:jelly xmlns:j=&quot;jelly:core&quot; xmlns:st=&quot;jelly:stapler&quot; xmlns:d=&quot;jelly:define&quot;&gt;
            &lt;j:set var=&quot;ENV&quot; value=&quot;${build.getEnvironment(listener)}&quot;/&gt; 
        </xsl:text>  
        <xsl:text disable-output-escaping="yes">
            &lt;!-- Environment INFO--&gt;
            &lt;table class=&quot;panel&quot; cellspacing=&quot;0&quot; cellpadding=&quot;0&quot; border=&quot;0&quot; width=&quot;600&quot; style=&quot;width:449.7pt;border-collapse:collapse&quot;&gt;
                &lt;tbody&gt;       
                    &lt;tr&gt;          
                        &lt;td valign=&quot;bottom&quot; nowrap=&quot;&quot; class=&quot;panelheader&quot; style=&quot;width:113.4pt;border:solid #c6d9f1 1pt;border-bottom:solid #D1D1D1 1pt;&quot;&gt;          
                            &lt;p style=&quot;padding:0;margin:0;&quot;&gt;
                                &lt;b&gt;&lt;span style=&quot;font-size:9pt;color:#365f91&quot;&gt;Environment&lt;/span&gt;&lt;/b&gt;
                            &lt;/p&gt;          
                        &lt;/td&gt;          
                        &lt;td valign=&quot;bottom&quot; nowrap=&quot;&quot;  style=&quot;width:336.3pt;border:none;border-bottom:solid #D1D1D1 1pt;padding:0 5.4pt 0 5.4pt;min-height:11.25pt&quot;&gt;&lt;/td&gt;       
                    &lt;/tr&gt;       
                    &lt;tr&gt;
                        &lt;td valign=&quot;bottom&quot; nowrap=&quot;&quot;  style=&quot;width:113.4pt;min-height:15.95pt&quot;&gt;          
                            &lt;p style=&quot;text-autospace:none&quot;&gt;
                                &lt;b&gt;&lt;span style=&quot;font-size:9pt;color:#1f497d&quot;&gt;Release: &lt;/span&gt;&lt;/b&gt;
                            &lt;/p&gt;          
                        &lt;/td&gt;          
                        &lt;td valign=&quot;bottom&quot; nowrap=&quot;&quot;  style=&quot;width:336.3pt;&quot;&gt;          
                            &lt;p style=&quot;text-autospace:none&quot;&gt;
                                &lt;span&gt;
        </xsl:text>        
		<xsl:value-of select="/reportdata/@release" />
        <xsl:text disable-output-escaping="yes">
                                &lt;/span&gt;
                            &lt;/p&gt;          
                        &lt;/td&gt;       
                    &lt;/tr&gt;       
                    &lt;tr&gt;
                        &lt;td valign=&quot;bottom&quot; nowrap=&quot;&quot;  style=&quot;width:113.4pt;min-height:15.95pt&quot;&gt;          
                            &lt;p style=&quot;text-autospace:none&quot;&gt;
                                &lt;b&gt;&lt;span style=&quot;font-size:9pt;color:#1f497d&quot;&gt;Version No: &lt;/span&gt;&lt;/b&gt;
                            &lt;/p&gt;          
                        &lt;/td&gt;          
                        &lt;td valign=&quot;bottom&quot; nowrap=&quot;&quot;  style=&quot;width:336.3pt;&quot;&gt;
                            &lt;p style=&quot;text-autospace:none&quot;&gt;
                                &lt;span&gt;
            </xsl:text>
            <xsl:value-of select="/reportdata/@version" />
            <xsl:text disable-output-escaping="yes">
                                &lt;/span&gt;
                            &lt;/p&gt;
                        &lt;/td&gt;
                    &lt;/tr&gt;
                    &lt;tr style=&quot;min-height:15.95pt&quot;&gt;
                        &lt;td valign=&quot;bottom&quot; nowrap=&quot;&quot;  style=&quot;width:113.4pt;min-height:15.95pt&quot;&gt;
                            &lt;p style=&quot;text-autospace:none&quot;&gt;
                                &lt;b&gt;&lt;span style=&quot;font-size:9pt;color:#1f497d&quot;&gt;Build Date:&lt;/span&gt;&lt;/b&gt;
                            &lt;/p&gt;
                        &lt;/td&gt;
                        &lt;td valign=&quot;bottom&quot; nowrap=&quot;&quot;  style=&quot;width:336.3pt;&quot;&gt;
                            &lt;p style=&quot;text-autospace:none&quot;&gt;
                                &lt;span&gt;
            </xsl:text>
            <xsl:value-of select="/reportdata/@builddate" />
            <xsl:text disable-output-escaping="yes">
                                &lt;/span&gt;
                            &lt;/p&gt;
                        &lt;/td&gt;
                    &lt;/tr&gt;
                    &lt;tr style=&quot;min-height:15.95pt&quot;&gt;
                        &lt;td valign=&quot;bottom&quot; nowrap=&quot;&quot;  style=&quot;width:113.4pt;min-height:15.95pt&quot;&gt;
                            &lt;p style=&quot;text-autospace:none&quot;&gt;
                                &lt;b&gt;&lt;span style=&quot;font-size:9pt;color:#1f497d&quot;&gt;Build URL: &lt;/span&gt;&lt;/b&gt;
                            &lt;/p&gt;
                        &lt;/td&gt;
                        &lt;td valign=&quot;bottom&quot; nowrap=&quot;&quot;  style=&quot;width:336.3pt;&quot;&gt;
                            &lt;p style=&quot;text-autospace:none&quot;&gt;
                                &lt;span&gt;
                                    &lt;a href=&quot;
            </xsl:text>                        
            ${rooturl}${build.url}
            <xsl:text disable-output-escaping="yes">
                                    &quot; target=&quot;_blank&quot;&gt;
            </xsl:text>                        
            ${rooturl}${build.url}
            <xsl:text disable-output-escaping="yes">
                                    &lt;/a&gt;
                                &lt;/span&gt;
                            &lt;/p&gt;
                        &lt;/td&gt;
                    &lt;/tr&gt;
                    &lt;tr style=&quot;min-height:15.95pt&quot;&gt;
                        &lt;td nowrap=&quot;&quot; valign=&quot;bottom&quot; style=&quot;width:113.4pt;min-height:15.95pt&quot;&gt;
                            &lt;p style=&quot;text-autospace:none&quot;&gt;
                                &lt;b&gt;&lt;span style=&quot;font-size:9pt;color:#1f497d&quot;&gt;Automation URL: &lt;/span&gt;&lt;/b&gt;
                            &lt;/p&gt;
                        &lt;/td&gt;
                        &lt;td valign=&quot;bottom&quot; nowrap=&quot;&quot;  style=&quot;width:336.3pt;&quot;&gt;
                            &lt;p style=&quot;text-autospace:none&quot;&gt;
                                &lt;span&gt;
                                    &lt;a href=&quot;
                </xsl:text>                        
                <xsl:value-of select="/reportdata/@automationurl" />
                <xsl:text disable-output-escaping="yes">                    
                                    &quot; target=&quot;_blank&quot;&gt;
                </xsl:text>                        
                <xsl:value-of select="/reportdata/@automationurl" />
                <xsl:text disable-output-escaping="yes">
                                    &lt;/a&gt;
                                &lt;/span&gt;
                            &lt;/p&gt;
                        &lt;/td&gt;
                    &lt;/tr&gt;
                &lt;/tbody&gt;
            &lt;/table&gt; 
        </xsl:text>                    
        <xsl:text disable-output-escaping="yes">     
        &lt;/j:jelly&gt;            
        </xsl:text>                         
    </xsl:template>    	
</xsl:stylesheet>