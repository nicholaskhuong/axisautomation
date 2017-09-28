<?xml version="1.0" encoding="UTF-8"?>
<!--
    @Description: Stylesheet to be used with Ant JUnitReport output. 
                  It creates a email template for report.
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
	
	<!--
    @Template: jelly.generate.report
    @Description: Generate jelly report for Jenkins trigger email
    @Author: TRAN Q. Cuong (Connor)
    -->
	<xsl:template match="/">
        <xsl:text disable-output-escaping="yes">
        &lt;j:jelly xmlns:j=&quot;jelly:core&quot; xmlns:st=&quot;jelly:stapler&quot; xmlns:d=&quot;jelly:define&quot;&gt;
            &lt;j:set var=&quot;ENV&quot; value=&quot;${build.getEnvironment(listener)}&quot;/&gt; 
        </xsl:text>  
        <xsl:text disable-output-escaping="yes">
            &lt;!-- CONNOR JELLY--&gt;
            &lt;table class=&quot;panel&quot; cellspacing=&quot;0&quot; cellpadding=&quot;0&quot; border=&quot;0&quot; width=&quot;529&quot; style=&quot;width:396.5pt;border-collapse:collapse&quot;&gt;
                &lt;tbody&gt; 
                    &lt;tr&gt;          
                        &lt;td valign=&quot;bottom&quot; nowrap=&quot;&quot; colspan=&quot;2&quot; class=&quot;panelheader&quot; style=&quot;width:113.4pt;border:solid #c6d9f1 1pt;border-bottom:solid #D1D1D1 1pt;&quot;&gt;          
                            &lt;p style=&quot;padding:0;margin:0;&quot; &gt;
                                &lt;b&gt;&lt;span style=&quot;font-size:9pt;color:#365f91&quot;&gt;Test Cases Summary&lt;/span&gt;&lt;/b&gt;
                            &lt;/p&gt;          
                        &lt;/td&gt;          
                        &lt;td valign=&quot;bottom&quot; colspan=&quot;3&quot; style=&quot;width:283.1pt;border:none;border-bottom:solid #D1D1D1 1pt;padding:0 5.4pt 0 5.4pt;min-height:11.25pt&quot;&gt;&lt;/td&gt;       
                    &lt;/tr&gt;       
                    &lt;tr&gt;
                        &lt;td width=&quot;24&quot; nowrap=&quot;&quot; class=&quot;gheader&quot; style=&quot;width:17.85pt;min-height:15.75pt;border-left:solid 1.0pt #D1D1D1&quot;&gt;
                            &lt;p align=&quot;center&quot; style=&quot;text-align:center&quot;&gt;&lt;b&gt;&lt;span&gt;No.&lt;/span&gt;&lt;/b&gt;&lt;/p&gt;
                        &lt;/td&gt;
                        &lt;td width=&quot;280&quot; nowrap=&quot;&quot; class=&quot;gheader&quot; style=&quot;width:200.55pt;&quot;&gt;
                            &lt;p&gt;&lt;b&gt;&lt;span&gt;Stream&lt;/span&gt;&lt;/b&gt;&lt;/p&gt;
                        &lt;/td&gt;
                        &lt;td width=&quot;108&quot; nowrap=&quot;&quot; class=&quot;gheader&quot; style=&quot;width:91.35pt;text-align:right&quot;&gt;
                            &lt;p&gt;&lt;b&gt;&lt;span&gt;Executed TCs&lt;/span&gt;&lt;/b&gt;&lt;/p&gt;
                        &lt;/td&gt;                             
                        &lt;td width=&quot;100&quot; nowrap=&quot;&quot; class=&quot;gheader&quot; style=&quot;width:79.0pt;text-align:right&quot;&gt;
                            &lt;p&gt;&lt;b&gt;&lt;span&gt;Passed&lt;/span&gt;&lt;/b&gt;&lt;/p&gt;
                        &lt;/td&gt;
                        &lt;td width=&quot;100&quot; nowrap=&quot;&quot; class=&quot;gheader&quot; style=&quot;width:79.0pt;text-align:right&quot;&gt;
                            &lt;p&gt;&lt;b&gt;&lt;span&gt;Failed&lt;/span&gt;&lt;/b&gt;&lt;/p&gt;
                        &lt;/td&gt;                   
                    &lt;/tr&gt;  
        </xsl:text> 
		<!--Package summary-->
		<xsl:apply-templates select="." mode="jelly.generate.packagesummary">		     
		</xsl:apply-templates>                          
			<xsl:text disable-output-escaping="yes">            
               &lt;/tbody&gt;
            &lt;/table&gt;
        </xsl:text> 
		
		<!--Package details-->
		<xsl:text disable-output-escaping="yes">     
        &lt;/j:jelly&gt;            
        </xsl:text>        
    </xsl:template> 
	
	<!--
    @Template: jelly.generate.packagesummary
    @Description: Generate jelly report summary for Jenkins trigger email
    @Author: TRAN Q. Cuong (Connor)
    -->
	<xsl:template match="/reportdata/packages" mode="jelly.generate.packagesummary">
		<xsl:text disable-output-escaping="yes">    
                    &lt;tr&gt;
                        &lt;td width=&quot;24&quot; nowrap=&quot;&quot; style=&quot;width:17.85pt;padding:0 4pt 0 4pt;&quot;&gt;
                            &lt;p tyle=&quot;text-align:center&quot;&gt;
                                &lt;span&gt;</xsl:text>
        <xsl:value-of select="position()" />
        <xsl:text disable-output-escaping="yes">&lt;/span&gt;
                            &lt;/p&gt;
                        &lt;/td&gt;
                        &lt;td width=&quot;280&quot; nowrap=&quot;&quot; style=&quot;width:200.55pt;padding:0 4pt 0 4pt;&quot;&gt; 
                            &lt;p style=&quot;text-autospace:none;padding:0;margin:0;&quot;&gt;
                                    &lt;a href=&quot;${ENV.get(&apos;JENKINS_URL&apos;)}job/${ENV.get(&apos;JOB_NAME&apos;)}/${ENV.get(&apos;BUILD_NUMBER&apos;)}/artifact/EllipseRegressionTests/report.html#?dev=1</xsl:text>&amp;id=<xsl:value-of select="@id" />&amp;<xsl:text disable-output-escaping="yes">query=</xsl:text><xsl:value-of select="@id" />		 
        <xsl:text disable-output-escaping="yes">&quot; 
                                        title=&quot;View </xsl:text><xsl:value-of select="@text" /><xsl:text disable-output-escaping="yes"> summary&quot; 
                                        target=&quot;_blank&quot; 
                                        style=&quot;color:#0B6ABA;cursor:pointer;outline:medium none;text-decoration:none&quot; 
                                        class=&quot;tootip link&quot;&gt;</xsl:text>
        <xsl:value-of select="@text" />
        <xsl:text disable-output-escaping="yes">&lt;/a&gt;
                            &lt;/p&gt;
                        &lt;/td&gt;
                        &lt;td width=&quot;108&quot; style=&quot;width:91.35pt;padding:0 4pt 0 4pt;&quot;&gt;
                          &lt;p align=&quot;right&quot; style=&quot;text-align:right&quot;&gt;</xsl:text>
        <xsl:choose>
            <xsl:when test="(@total &gt; 0)">                            
                <xsl:text disable-output-escaping="yes">&lt;a href=&quot;${ENV.get(&apos;JENKINS_URL&apos;)}job/${ENV.get(&apos;JOB_NAME&apos;)}/${ENV.get(&apos;BUILD_NUMBER&apos;)}/artifact/EllipseRegressionTests/report.html#?dev=1</xsl:text>&amp;id=<xsl:value-of select="@id" />&amp;query=<xsl:value-of select="@id" />
                <xsl:text disable-output-escaping="yes">&quot; 
                                                        title=&quot;View summary&quot; 
                                                        target=&quot;_blank&quot; 
                                                        style=&quot;color:#0B6ABA;cursor:pointer;outline:medium none;text-decoration:none&quot; 
                                                        class=&quot;tootip link&quot;&gt;</xsl:text>
                <xsl:value-of select="@total" />
                <xsl:text disable-output-escaping="yes">
                                    &lt;/a&gt;</xsl:text>                              
            </xsl:when>
            <xsl:otherwise>
                                    0
            </xsl:otherwise>
        </xsl:choose>
        <xsl:text disable-output-escaping="yes">&lt;/p&gt;
                        &lt;/td&gt;
                        &lt;td width=&quot;100&quot; nowrap=&quot;&quot; style=&quot;width:79.0pt;padding:0 4pt 0 4pt;&quot;&gt;
                            &lt;p align=&quot;right&quot; style=&quot;text-align:right&quot;&gt;</xsl:text>   
        <xsl:choose>
            <xsl:when test="(@pass &gt; 0)">  
                <xsl:text disable-output-escaping="yes">&lt;a href=&quot;${ENV.get(&apos;JENKINS_URL&apos;)}job/${ENV.get(&apos;JOB_NAME&apos;)}/${ENV.get(&apos;BUILD_NUMBER&apos;)}/artifact/EllipseRegressionTests/report.html#?dev=1</xsl:text>&amp;id=<xsl:value-of select="@id" />&amp;status=pass&amp;query=<xsl:value-of select="@id" />
                <xsl:text disable-output-escaping="yes">&quot; 
                                                    title=&quot;View passed summary&quot; 
                                                    target=&quot;_blank&quot; 
                                                    style=&quot;color:Green;cursor:pointer;outline:medium none;text-decoration:none&quot; 
                                                    class=&quot;tootip link&quot;&gt;</xsl:text>
                <xsl:value-of select="@pass" />
                <xsl:text disable-output-escaping="yes">
                                        &lt;/a&gt;</xsl:text> 
            </xsl:when>
            <xsl:otherwise>
                                    0
            </xsl:otherwise>
        </xsl:choose>
        <xsl:text disable-output-escaping="yes">                                        
                          &lt;/p&gt;
                        &lt;/td&gt;               
                        &lt;td width=&quot;100&quot; nowrap=&quot;&quot; style=&quot;width:79.0pt;padding:0 4pt 0 4pt;&quot;&gt;
                            &lt;p align=&quot;right&quot; style=&quot;text-align:right&quot;&gt;</xsl:text>    
        <xsl:choose>
            <xsl:when test="(@fail &gt; 0)">  
                <xsl:text disable-output-escaping="yes">&lt;a href=&quot;${ENV.get(&apos;JENKINS_URL&apos;)}job/${ENV.get(&apos;JOB_NAME&apos;)}/${ENV.get(&apos;BUILD_NUMBER&apos;)}/artifact/EllipseRegressionTests/report.html#?dev=1</xsl:text>&amp;id=<xsl:value-of select="@id" />&amp;status=fail&amp;query=<xsl:value-of select="@id" />
                <xsl:text disable-output-escaping="yes">&quot; 
                                                    title=&quot;View failed summary&quot; 
                                                    target=&quot;_blank&quot; 
                                                    style=&quot;color:#DC3912;cursor:pointer;outline:medium none;text-decoration:none&quot; 
                                                    class=&quot;tootip link&quot;&gt;</xsl:text>
                <xsl:value-of select="@fail" />
                <xsl:text disable-output-escaping="yes">
                                                    &lt;/a&gt;</xsl:text> 
            </xsl:when>
            <xsl:otherwise>
                                        0
            </xsl:otherwise>
        </xsl:choose>
        <xsl:text disable-output-escaping="yes">                               
                          &lt;/p&gt;
                        &lt;/td&gt;               
                    &lt;/tr&gt;     
        </xsl:text>
		
		<xsl:variable name="packageId" select="@id"/>

        <xsl:choose>
            <xsl:when test="(position() = last())">  
                <xsl:text disable-output-escaping="yes">        
                &lt;tr&gt;            
                    &lt;td width=&quot;280&quot; colspan=&quot;2&quot; nowrap=&quot;&quot; style=&quot;width:200.55pt;padding:0 4pt 0 4pt;&quot;&gt;
                        &lt;p style=&quot;text-autospace:none;padding:0;margin:0;&quot;&gt;
                                &lt;b&gt;Total&lt;/b&gt;
                        &lt;/p&gt;
                    &lt;/td&gt;
                    &lt;td width=&quot;108&quot; style=&quot;width:91.35pt;padding:0 4pt 0 4pt;&quot;&gt;
                      &lt;p align=&quot;right&quot; style=&quot;text-align:right&quot;&gt;</xsl:text>
                <xsl:choose>
                    <xsl:when test="(..//@total &gt; 0)">                            
                        <xsl:text disable-output-escaping="yes">&lt;a href=&quot;${ENV.get(&apos;JENKINS_URL&apos;)}job/${ENV.get(&apos;JOB_NAME&apos;)}/${ENV.get(&apos;BUILD_NUMBER&apos;)}/artifact/EllipseRegressionTests/report.html#?dev=1&quot; 
                                                    title=&quot;View summary&quot; 
                                                    target=&quot;_blank&quot; 
                                                    style=&quot;color:#0B6ABA;cursor:pointer;outline:medium none;text-decoration:none&quot; 
                                                    class=&quot;tootip link&quot;&gt;&lt;b&gt;</xsl:text>
                        <xsl:value-of select="..//@total" />
                        <xsl:text disable-output-escaping="yes">
                                &lt;/b&gt;&lt;/a&gt;</xsl:text>                              
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:text disable-output-escaping="yes">&lt;b&gt;0&lt;/b&gt;</xsl:text>
                    </xsl:otherwise>
                </xsl:choose>
                <xsl:text disable-output-escaping="yes">&lt;/p&gt;
                    &lt;/td&gt;
                    &lt;td width=&quot;100&quot; nowrap=&quot;&quot; style=&quot;width:79.0pt;padding:0 4pt 0 4pt;&quot;&gt;
                        &lt;p align=&quot;right&quot; style=&quot;text-align:right&quot;&gt;</xsl:text>   
                <xsl:choose>
                    <xsl:when test="(../@pass &gt; 0)">  
                        <xsl:text disable-output-escaping="yes">&lt;a href=&quot;${ENV.get(&apos;JENKINS_URL&apos;)}job/${ENV.get(&apos;JOB_NAME&apos;)}/${ENV.get(&apos;BUILD_NUMBER&apos;)}/artifact/EllipseRegressionTests/report.html#?dev=1</xsl:text>&amp;status=pass&quot; 
                        <xsl:text disable-output-escaping="yes">title=&quot;View passed summary&quot; 
                                                target=&quot;_blank&quot; 
                                                style=&quot;color:Green;cursor:pointer;outline:medium none;text-decoration:none&quot; 
                                                class=&quot;tootip link&quot;&gt;&lt;b&gt;</xsl:text>
                        <xsl:value-of select="..//@pass" />
                        <xsl:text disable-output-escaping="yes">
                                    &lt;/b&gt;&lt;/a&gt;</xsl:text> 
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:text disable-output-escaping="yes">&lt;b&gt;0&lt;/b&gt;</xsl:text>
                    </xsl:otherwise>
                </xsl:choose>
                <xsl:text disable-output-escaping="yes">                                        
                      &lt;/p&gt;
                    &lt;/td&gt;               
                    &lt;td width=&quot;100&quot; nowrap=&quot;&quot; style=&quot;width:79.0pt;padding:0 4pt 0 4pt;&quot;&gt;
                        &lt;p align=&quot;right&quot; style=&quot;text-align:right&quot;&gt;</xsl:text>    
                <xsl:choose>
                    <xsl:when test="(..//@fail &gt; 0)">  
                        <xsl:text disable-output-escaping="yes">&lt;a href=&quot;${ENV.get(&apos;JENKINS_URL&apos;)}job/${ENV.get(&apos;JOB_NAME&apos;)}/${ENV.get(&apos;BUILD_NUMBER&apos;)}/artifact/EllipseRegressionTests/report.html#?dev=1</xsl:text>&amp;status=fail&quot; 
                        <xsl:text disable-output-escaping="yes">title=&quot;View failed summary&quot; 
                                                target=&quot;_blank&quot; 
                                                style=&quot;color:#DC3912;cursor:pointer;outline:medium none;text-decoration:none&quot; 
                                                class=&quot;tootip link&quot;&gt;&lt;b&gt;</xsl:text>
                        <xsl:value-of select="..//@fail" />
                        <xsl:text disable-output-escaping="yes">
                                                &lt;/b&gt;&lt;/a&gt;</xsl:text> 
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:text disable-output-escaping="yes">&lt;b&gt;0&lt;/b&gt;</xsl:text>
                    </xsl:otherwise>
                </xsl:choose>
                <xsl:text disable-output-escaping="yes">                               
                                    &lt;/p&gt;
                                &lt;/td&gt;               
                                &lt;/tr&gt;     
                </xsl:text>   
            </xsl:when>
            <xsl:otherwise>                    
            </xsl:otherwise>
        </xsl:choose> 
	</xsl:template> 
	
	<!--
    @Template: jelly.generate.packagedetails
    @Description: Generate jelly report details for Jenkins trigger email
    @Author: TRAN Q. Cuong (Connor)
    -->
	<xsl:template match="/reportdata/packages" mode="jelly.generate.packagedetails">
		<xsl:choose>
            <xsl:when test="(@fail &gt; 0)">                
				<xsl:variable name="package_id">
					<xsl:value-of select="@id" />
				</xsl:variable>
                <!--Define loop count for each suite-->                    
                <xsl:text disable-output-escaping="yes">
                    &lt;j:set var=&quot;jvar_</xsl:text><xsl:value-of select="$package_id" /><xsl:text disable-output-escaping="yes">_Count&quot; value=&quot;0&quot;/&gt;
                </xsl:text>
                <xsl:text disable-output-escaping="yes">&lt;p style=&quot;margin-right:0in;margin-left:5pt&quot;&gt;&lt;b&gt;</xsl:text>
                <xsl:value-of select="position()" />. <xsl:value-of select="@text" /> Stream
                <xsl:text disable-output-escaping="yes">&lt;/b&gt;&lt;/p&gt;</xsl:text>

                <xsl:text disable-output-escaping="yes">
                    &lt;!-- CONNOR JELLY--&gt;
                    &lt;table class=&quot;panel&quot; cellspacing=&quot;0&quot; cellpadding=&quot;0&quot; border=&quot;0&quot; width=&quot;755&quot; style=&quot;width:652.1pt;border-collapse:collapse&quot;&gt;
                        &lt;tbody&gt; 
                            &lt;tr&gt;          
                                &lt;td valign=&quot;bottom&quot; nowrap=&quot;&quot; colspan=&quot;2&quot; class=&quot;panelheader&quot; width=&quot;383&quot; style=&quot;width:287.6pt;border:solid #c6d9f1 1pt;border-bottom:solid #D1D1D1 1pt;&quot;&gt;          
                                    &lt;p style=&quot;padding:0;margin:0;&quot; &gt;
                                        &lt;b&gt;&lt;span style=&quot;font-size:9pt;color:#365f91&quot;&gt;Failed test cases details&lt;/span&gt;&lt;/b&gt;
                                    &lt;/p&gt;          
                                &lt;/td&gt;          
                                &lt;td valign=&quot;bottom&quot; colspan=&quot;2&quot; style=&quot;width:283.1pt;border:none;border-bottom:solid #D1D1D1 1pt;padding:0 5.4pt 0 5.4pt;min-height:11.25pt&quot;&gt;&lt;/td&gt;       
                            &lt;/tr&gt;       
                            &lt;tr&gt;       
                                &lt;td width=&quot;24&quot; nowrap=&quot;&quot; class=&quot;gheader&quot; style=&quot;width:17.85pt;min-height:15.75pt;border-left:solid 1.0pt #D1D1D1&quot;&gt;
                                    &lt;p align=&quot;center&quot; style=&quot;text-align:center&quot;&gt;&lt;b&gt;&lt;span&gt;No.&lt;/span&gt;&lt;/b&gt;&lt;/p&gt;
                                &lt;/td&gt;
                                &lt;td width=&quot;383&quot; nowrap=&quot;&quot; class=&quot;gheader&quot; style=&quot;width:287.6pt;min-height:15.75pt;border-left:solid 1.0pt #D1D1D1&quot;&gt;
                                    &lt;p&gt;&lt;b&gt;&lt;span&gt;Test Cases&lt;/span&gt;&lt;/b&gt;&lt;/p&gt;
                                &lt;/td&gt;								
                                &lt;td width=&quot;114&quot; nowrap=&quot;&quot; class=&quot;gheader&quot; style=&quot;width:85.5pt;text-align:left&quot;&gt;
                                    &lt;p&gt;&lt;b&gt;&lt;span&gt;Time&lt;/span&gt;&lt;/b&gt;&lt;/p&gt;
                                &lt;/td&gt;  
								&lt;td width=&quot;114&quot; nowrap=&quot;&quot; class=&quot;gheader&quot; style=&quot;width:85.5pt;text-align:left&quot;&gt;
                                    &lt;p&gt;&lt;b&gt;&lt;span&gt;Reasons&lt;/span&gt;&lt;/b&gt;&lt;/p&gt;
                                &lt;/td&gt;
                            &lt;/tr&gt;  
                </xsl:text>                
				<!--Show failed testcases-->
                <xsl:for-each select="groups/suites/testcases[@status=0]">     					
					<!--Increase testcase index-->                    					
					<xsl:text disable-output-escaping="yes">
						&lt;j:set var=&quot;jvar_</xsl:text><xsl:value-of select="$package_id" /><xsl:text disable-output-escaping="yes">_Count&quot; value=&quot;${jvar_</xsl:text><xsl:value-of select="$package_id" /><xsl:text disable-output-escaping="yes">_Count + 1}&quot;/&gt;
					</xsl:text>					
					<xsl:text disable-output-escaping="yes">    
					&lt;tr&gt;
					</xsl:text>					
					<xsl:text disable-output-escaping="yes">    						
						&lt;td width=&quot;24&quot; valign=&quot;top&quot; nowrap=&quot;&quot; style=&quot;width:17.85pt;padding:0 4pt 0 4pt;&quot;&gt;
							&lt;p tyle=&quot;text-align:center&quot;&gt;
								&lt;span&gt;								
								${jvar_</xsl:text><xsl:value-of select="$package_id" /><xsl:text disable-output-escaping="yes">_Count}								
								&lt;/span&gt;
							&lt;/p&gt;
						&lt;/td&gt;                                    
						&lt;td width=&quot;383&quot; valign=&quot;top&quot; nowrap=&quot;nowrap&quot; style=&quot;width:287.6pt;padding:0 4pt 0 4pt;&quot;&gt;
						&lt;p style=&quot;text-autospace:none;padding:0;margin:0;width:287.1pt;&quot;&gt;
						&lt;a href=&quot;${ENV.get(&apos;JENKINS_URL&apos;)}job/${ENV.get(&apos;JOB_NAME&apos;)}/${ENV.get(&apos;BUILD_NUMBER&apos;)}/artifact/EllipseRegressionTests/report.html#?dev=1</xsl:text>&amp;id=<xsl:value-of select="@id"/>
					<xsl:text disable-output-escaping="yes">&quot; 
						title=&quot;View testcase summary&quot; 
						style=&quot;color:#DC3912;cursor:pointer;outline:medium none;text-decoration:none&quot; 
						target=&quot;_blank&quot; 
					class=&quot;tootip link&quot;&gt;</xsl:text>
					<xsl:value-of select="@text" />
					<xsl:text disable-output-escaping="yes">
							&lt;/a&gt;&lt;/p&gt;
						&lt;/td&gt;						
					</xsl:text>
					<xsl:text disable-output-escaping="yes">            							
						&lt;td width=&quot;114&quot; valign=&quot;top&quot; style=&quot;width:85.5pt;padding:0 4pt 0 4pt;&quot;&gt;
							&lt;p align=&quot;right&quot; style=&quot;width:85pt;text-align:right;color:#1F497D;cursor:pointer;outline:medium none;text-decoration:none&quot;&gt;
					</xsl:text>
					<xsl:call-template name="func.ConvertSecsToHumanizedTimeSpan">
						<xsl:with-param name="param_Seconds" select="@duration"/>
					</xsl:call-template> 
					<xsl:text disable-output-escaping="yes">            
							&lt;/p&gt;
						&lt;/td&gt; 
					</xsl:text>						
					<xsl:text disable-output-escaping="yes">            
						&lt;td width=&quot;114&quot; valign=&quot;top&quot; style=&quot;width:85.5pt;padding:0 4pt 0 4pt;&quot;&gt;							
					</xsl:text>						
					<xsl:for-each select="steps[@stack!='']"> 
						<xsl:text disable-output-escaping="yes">&lt;span style=&quot;padding:0;margin:0;line-height:10px&quot;&gt;&lt;u&gt;</xsl:text><xsl:value-of select="@text" /><xsl:text disable-output-escaping="yes">&lt;/u&gt;</xsl:text>: <xsl:value-of select="@act" />
						<xsl:variable name="stepexectime">
							<xsl:call-template name="func.ConvertSecsToHumanizedTimeSpan">
								<xsl:with-param name="param_Seconds" select="@duration"/>
							</xsl:call-template>
						</xsl:variable>						
						(<xsl:choose>
							<xsl:when test="string-length($stepexectime) > 0">
								<xsl:value-of select="$stepexectime" />
							</xsl:when>
							<xsl:otherwise>             
								<xsl:value-of select="'0 sec'" />                    
							</xsl:otherwise>
						</xsl:choose>)
						<xsl:text disable-output-escaping="yes">&lt;/span&gt;</xsl:text>		
						<xsl:text disable-output-escaping="yes">&lt;pre style=&quot;color:#DC3912;padding:0;margin:0&quot;&gt;</xsl:text>
						<xsl:call-template name="func.SubstringBefore">
							<xsl:with-param name="param_Input" select="@stack" />
							<xsl:with-param name="param_Token" select="'&#10;          &#10;'" />
						</xsl:call-template>						
						<xsl:text disable-output-escaping="yes">&lt;/pre&gt;</xsl:text>		
					</xsl:for-each>
					<xsl:text disable-output-escaping="yes">							
						&lt;/td&gt;
					</xsl:text>	
					<xsl:text disable-output-escaping="yes">            
						&lt;/tr&gt;     
					</xsl:text>						
                </xsl:for-each>
                <xsl:text disable-output-escaping="yes">            
                       &lt;/tbody&gt;
                    &lt;/table&gt;
                </xsl:text>                          
            </xsl:when>
            <xsl:otherwise>      
                <xsl:text disable-output-escaping="yes">&lt;p style=&quot;margin-right:0in;margin-left:5pt&quot;&gt;&lt;b&gt;</xsl:text>
                <xsl:value-of select="position()" />. <xsl:value-of select="@text" /> Stream
                <xsl:text disable-output-escaping="yes">&lt;/b&gt;&lt;/p&gt;</xsl:text>                
                &lt;i style=&quot;padding-left:10pt&quot;&gt;No failed test cases&lt;/i&gt;
            </xsl:otherwise>
        </xsl:choose>   
	</xsl:template> 
	
	<!--
    @Template: func.ConvertSecsToHumanizedTimeSpan
    @Description: Convert second to humanized time span 
    @Author: TRAN Q. Cuong (Connor)
    -->    
    <xsl:template name="func.ConvertSecsToHumanizedTimeSpan">
        <xsl:param name="param_Seconds"/> 
        <xsl:variable name="hours" select="floor($param_Seconds div (60 * 60))"/>
        <xsl:variable name="divisor_for_minutes" select="$param_Seconds mod (60 * 60)"/>
        <xsl:variable name="minutes" select="floor($divisor_for_minutes div 60)"/>
        <xsl:variable name="divisor_for_seconds" select="$divisor_for_minutes mod 60"/>
        <xsl:variable name="secs" select="ceiling($divisor_for_seconds)"/>
        <xsl:if test="($hours &gt; 0)">
            <xsl:value-of select=" $hours"/>
			<xsl:text disable-output-escaping="yes"> hr </xsl:text>			
        </xsl:if>
        <xsl:if test="($minutes &gt; 0)">
            <xsl:value-of select=" $minutes"/>            
			<xsl:text disable-output-escaping="yes"> min </xsl:text>			
        </xsl:if>
        <xsl:if test="($secs &gt; 0)">
            <xsl:value-of select=" $secs"/>
            <xsl:text disable-output-escaping="yes"> sec</xsl:text>			
        </xsl:if>        
    </xsl:template> 
	
	<!--
    @Template: func.SubstringBefore
    @Description: 
    @Author: Axis Team -->
    <xsl:template name="func.SubstringBefore">
        <xsl:param name="param_Input" />
        <xsl:param name="param_Token" />        
        <xsl:variable name="returnValue">
            <xsl:value-of select="substring-before($param_Input, $param_Token)" />
        </xsl:variable>
        
		<xsl:variable name="tmp">
			<xsl:choose>
				<xsl:when test="string-length($returnValue) &gt; 0">
					<xsl:value-of select="$returnValue" />
				</xsl:when>
				<xsl:otherwise>             
					<xsl:value-of select="$param_Input" />                    
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>       
		<xsl:call-template name="func.string.FixLength">
			<xsl:with-param name="text" select="$tmp" />
			<xsl:with-param name="length" select="150" />			
		</xsl:call-template>
    </xsl:template>
	
	<!--    
    @Template: func.string.FixLength
    @Description: 
    @Author: Axis Team    -->
    <xsl:template name="func.string.FixLength">
        <xsl:param name="text" />
        <xsl:param name="length" />
        
        <xsl:choose>
			<xsl:when test="string-length($text) &gt; $length">                        
				<xsl:value-of select="concat(substring($text, 1, $length), '...')" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$text" />
			</xsl:otherwise>
		</xsl:choose>
    </xsl:template> 
</xsl:stylesheet>