<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" version="1.1">
   <xsl:template match="customroot">
      <fo:root>
         <fo:layout-master-set>
            <fo:simple-page-master master-name="simple" page-width="8in" page-height="auto" media-usage="unbounded">
               <fo:region-body margin-top="0in" margin-bottom="0cm" page-width="8in" page-height="auto" media-usage="unbounded" margin-left="0cm" margin-right="0cm" background-repeat="repeat" />
               <fo:region-before margin-top="0in" margin-left="0in" />
               <fo:region-after extent="0cm" margin-right="0cm" margin-bottom="0cm" />
            </fo:simple-page-master>
         </fo:layout-master-set>
         <fo:page-sequence master-reference="simple">
            <fo:flow flow-name="xsl-region-body">
               <fo:block>
                  <fo:leader leader-pattern="space" />
               </fo:block>
               <fo:block text-align="left" margin-left="15px">
                  <fo:external-graphic max-height="40px" content-width="scale-to-fit" scaling="uniform" src="./images/tmblogo.png" />
               </fo:block>
               <fo:block language="th" font-size="10pt" />
               <!--  3rd block starts   -->
               <fo:table border-bottom-style="none" border-left-style="none" border-right-style="none" border-top-style="none" table-layout="fixed" width="600px" height="650px" border="solid 1pt gray" border-spacing="2pt" margin-left="0cm" margin-top="0cm" background-repeat="repeat">
                  <fo:table-body start-indent="0pt">
                     <fo:table-row>
                        <fo:table-cell border-bottom-style="none" border-left-style="none" border-right-style="none" border-top-style="none" border="solid 1pt gray" padding="2pt">
                           <!--  Inner table logo bg starts   -->
                           <fo:table border-bottom-style="none" border-left-style="none" border-right-style="none" border-top-style="none" table-layout="fixed" width="320px" border="solid 1pt gray" border-spacing="2pt">
                              <fo:table-body start-indent="0pt">
                                 <fo:table-row>
                                    <fo:table-cell border-bottom-style="none" border-left-style="none" border-right-style="none" border-top-style="none" border="solid 1pt gray" padding="1pt">
                                       <fo:block-container width="600px" height="30px" margin-left="5px" margin-top="10px">
                                          <fo:block margin-left="0px" text-align="left" font-family="DBOzoneX-Bold" font-size="18pt" color="#000000">
                                             <fo:inline>ความยินยอมในการเปิดเผยข้อมูลผ่านระบบอินเทอร์เน็ต</fo:inline>
                                          </fo:block>
                                       </fo:block-container>
                                    </fo:table-cell>
                                 </fo:table-row>
                                 
                                 <fo:table-row>
                                    <fo:table-cell border-bottom-style="none" border-left-style="none" border-right-style="none" border-top-style="none" border="solid 1pt gray" padding="2pt">
                                       <fo:block-container width="230px" height="15px" margin-left="5px" margin-top="8px">
                                          <fo:block text-align="left" font-family="DBOzoneX" color="#000000" font-size="14pt">
                                             <fo:inline>ชื่อ -นามสกุล :</fo:inline>
                                          </fo:block>
                                       </fo:block-container>
                                    </fo:table-cell>
                                    <fo:table-cell border-bottom-style="none" border-left-style="none" border-right-style="none" border-top-style="none" border="solid 1pt gray" padding="2pt">
                                       <fo:block-container width="370px" height="15px" margin-right="1px" margin-top="8px">
                                          <fo:block text-align="right" font-family="DBOzoneX" color="#000000" font-size="14pt">
                                             <fo:inline>
                                                <xsl:value-of select="NCBCustName" />
                                             </fo:inline>
                                          </fo:block>
                                       </fo:block-container>
                                    </fo:table-cell>
                                 </fo:table-row>
                                 
                                 <fo:table-row>
                                    <fo:table-cell border-bottom-style="none" border-left-style="none" border-right-style="none" border-top-style="none" border="solid 1pt gray" padding="2pt">
                                       <fo:block-container width="230px" height="15px" margin-left="5px" margin-top="8px">
                                          <fo:block text-align="left" font-family="DBOzoneX" color=" #000000" font-size="14pt">
                                             <fo:inline>บัตรประจำตัวประชาชน :</fo:inline>
                                          </fo:block>
                                       </fo:block-container>
                                    </fo:table-cell>
                                    <fo:table-cell border-bottom-style="none" border-left-style="none" border-right-style="none" border-top-style="none" border="solid 1pt gray" padding="2pt">
                                       <fo:block-container width="370px" height="15px" margin-right="1px" margin-top="8px">
                                          <fo:block text-align="right" font-family="DBOzoneX" color="#000000" font-size="14pt">
                                             <fo:inline>
                                                <xsl:value-of select="NCBID" />
                                             </fo:inline>
                                          </fo:block>
                                       </fo:block-container>
                                    </fo:table-cell>
                                 </fo:table-row>
                                 
                                 <fo:table-row>
                                    <fo:table-cell border-bottom-style="none" border-left-style="none" border-right-style="none" border-top-style="none" border="solid 1pt gray" padding="2pt">
                                       <fo:block-container width="230px" height="15px" margin-left="5px" margin-top="8px">
                                          <fo:block text-align="left" font-family="DBOzoneX" color=" #000000" font-size="14pt">
                                             <fo:inline>วัน/เดือน/ปี พ.ศ.เกิด :</fo:inline>
                                          </fo:block>
                                       </fo:block-container>
                                    </fo:table-cell>
                                    <fo:table-cell border-bottom-style="none" border-left-style="none" border-right-style="none" border-top-style="none" border="solid 1pt gray" padding="2pt">
                                       <fo:block-container width="370px" height="15px" margin-right="1px" margin-top="8px">
                                          <fo:block text-align="right" font-family="DBOzoneX" color="#000000" font-size="14pt">
                                             <fo:inline>
                                                <xsl:value-of select="NCBDateofbirth" />
                                             </fo:inline>
                                          </fo:block>
                                       </fo:block-container>
                                    </fo:table-cell>
                                 </fo:table-row>
                                 
                                 <fo:table-row>
                                    <fo:table-cell border-bottom-style="none" border-left-style="none" border-right-style="none" border-top-style="none" border="solid 1pt gray" padding="2pt">
                                       <fo:block-container width="230px" height="15px" margin-left="5px" margin-top="8px">
                                          <fo:block text-align="left" font-family="DBOzoneX" color=" #000000" font-size="14pt">
                                             <fo:inline>หมายเลขโทรศัพท์เคลื่อนที่ :</fo:inline>
                                          </fo:block>
                                       </fo:block-container>
                                    </fo:table-cell>
                                    <fo:table-cell border-bottom-style="none" border-left-style="none" border-right-style="none" border-top-style="none" border="solid 1pt gray" padding="2pt">
                                       <fo:block-container width="370px" height="15px" margin-right="1px" margin-top="8px">
                                          <fo:block text-align="right" font-family="DBOzoneX" color="#000000" font-size="14pt">
                                             <fo:inline>
                                                <xsl:value-of select="NCBMobileNo" />
                                             </fo:inline>
                                          </fo:block>
                                       </fo:block-container>
                                    </fo:table-cell>
                                 </fo:table-row>
                                 
                                 <fo:table-row>
                                    <fo:table-cell border-bottom-style="none" border-left-style="none" border-right-style="none" border-top-style="none" border="solid 1pt gray" padding="2pt">
                                       <fo:block-container width="230px" height="15px" margin-left="5px" margin-top="8px">
                                          <fo:block text-align="left" font-family="DBOzoneX" color=" #000000" font-size="14pt">
                                             <fo:inline>ประเภทสินเชื่อ :</fo:inline>
                                          </fo:block>
                                       </fo:block-container>
                                    </fo:table-cell>
                                    <fo:table-cell border-bottom-style="none" border-left-style="none" border-right-style="none" border-top-style="none" border="solid 1pt gray" padding="2pt">
                                       <fo:block-container width="370px" height="15px" margin-right="1px" margin-top="8px">
                                          <fo:block text-align="right" font-family="DBOzoneX" color="#000000" font-size="14pt">
                                             <fo:inline>
                                                <xsl:value-of select="ProductName" />
                                             </fo:inline>
                                          </fo:block>
                                       </fo:block-container>
                                    </fo:table-cell>
                                 </fo:table-row>
                                 
                                 <fo:table-row>
                                    <fo:table-cell border-bottom-style="none" border-left-style="none" border-right-style="none" border-top-style="none" border="solid 1pt gray" padding="2pt">
                                       <fo:block-container width="230px" height="15px" margin-left="5px" margin-top="8px">
                                          <fo:block text-align="left" font-family="DBOzoneX" color=" #000000" font-size="14pt">
                                             <fo:inline>หมายเลขอ้างอิง :</fo:inline>
                                          </fo:block>
                                       </fo:block-container>
                                    </fo:table-cell>
                                    <fo:table-cell border-bottom-style="none" border-left-style="none" border-right-style="none" border-top-style="none" border="solid 1pt gray" padding="2pt">
                                       <fo:block-container width="370px" height="15px" margin-right="1px" margin-top="8px">
                                          <fo:block text-align="right" font-family="DBOzoneX" color="#000000" font-size="14pt">
                                             <fo:inline>
                                                <xsl:value-of select="NCBReferenceID" />
                                             </fo:inline>
                                          </fo:block>
                                       </fo:block-container>
                                    </fo:table-cell>
                                 </fo:table-row>
                                 
                                 <fo:table-row>
                                    <fo:table-cell border-bottom-style="none" border-left-style="none" border-right-style="none" border-top-style="none" border="solid 1pt gray" padding="2pt">
                                       <fo:block-container width="230px" height="15px" margin-left="5px" margin-top="8px">
                                          <fo:block text-align="left" font-family="DBOzoneX" color=" #000000" font-size="14pt">
                                             <fo:inline>วันเวลาที่ให้ความยินยอม :</fo:inline>
                                          </fo:block>
                                       </fo:block-container>
                                    </fo:table-cell>
                                    <fo:table-cell border-bottom-style="none" border-left-style="none" border-right-style="none" border-top-style="none" border="solid 1pt gray" padding="2pt">
                                       <fo:block-container width="370px" height="15px" margin-right="1px" margin-top="8px">
                                          <fo:block text-align="right" font-family="DBOzoneX" color="#000000" font-size="14pt">
                                             <fo:inline>
                                                <xsl:value-of select="NCBDateTime" />
                                             </fo:inline>
                                          </fo:block>
                                       </fo:block-container>
                                    </fo:table-cell>
                                 </fo:table-row>
                                 
                                 <fo:table-row>
                                    <fo:table-cell border-bottom-style="none" border-left-style="none" border-right-style="none" border-top-style="none" border="solid 1pt gray" padding="2pt">
                                       <fo:block-container width="230px" height="15px" margin-left="5px" margin-top="8px">
                                          <fo:block text-align="left" font-family="DBOzoneX" color=" #000000" font-size="14pt">
                                             <fo:inline>ช่องทางที่ทำรายการ :</fo:inline>
                                          </fo:block>
                                       </fo:block-container>
                                    </fo:table-cell>
                                    <fo:table-cell border-bottom-style="none" border-left-style="none" border-right-style="none" border-top-style="none" border="solid 1pt gray" padding="2pt">
                                       <fo:block-container width="370px" height="15px" margin-right="1px" margin-top="8px">
                                          <fo:block text-align="right" font-family="DBOzoneX" color="#000000" font-size="14pt">
                                             <xsl:choose>
                                                <xsl:when test="localeId='th_TH'">
                                                   <fo:inline font-weight="bold" />
                                                   <fo:inline>ทีเอ็มบี ทัช</fo:inline>
                                                </xsl:when>
                                                <xsl:otherwise>
                                                   <fo:inline>TTB APP</fo:inline>
                                                </xsl:otherwise>
                                             </xsl:choose>
                                          </fo:block>
                                       </fo:block-container>
                                    </fo:table-cell>
                                 </fo:table-row>
                                 
                                 <fo:table-row>
                                    <fo:table-cell border-bottom-style="none" border-left-style="none" border-right-style="none" border-top-style="none" border="solid 1pt gray" padding="2pt">
                                       <fo:block-container width="230px" height="15px" margin-left="5px" margin-top="8px">
                                          <fo:block text-align="left" font-family="DBOzoneX" color=" #000000" font-size="14pt">
                                             <fo:inline>วิธีการให้ความยินยอม :</fo:inline>
                                          </fo:block>
                                       </fo:block-container>
                                    </fo:table-cell>
                                    <fo:table-cell border-bottom-style="none" border-left-style="none" border-right-style="none" border-top-style="none" border="solid 1pt gray" padding="2pt">
                                       <fo:block-container width="370px" height="15px" margin-right="1px" margin-top="8px">
                                          <fo:block text-align="right" font-family="DBOzoneX" color="#000000" font-size="14pt">
                                             <fo:inline>
                                                <xsl:value-of select="ConsentbyCustomer" />
                                             </fo:inline>
                                          </fo:block>
                                       </fo:block-container>
                                    </fo:table-cell>
                                 </fo:table-row>
                                 
                                 <fo:table-row>
                                    <fo:table-cell text-align="justify">
                                       <fo:block-container width="560px" height="10px" margin-left="5px" margin-top="15px">
                                          <fo:block text-align="left" font-family="DBOzoneX-Bold" font-size="14pt" font-weight="normal" color=" #000000">
                                             <fo:inline />
                                          </fo:block>
                                       </fo:block-container>
                                    </fo:table-cell>
                                 </fo:table-row>
                                 
                                 <fo:table-row>
                                    <fo:table-cell text-align="justify">
                                       <fo:block-container width="560px" height="10px" margin-left="5px" margin-top="15px">
                                          <fo:block text-align="left" text-decoration="underline" font-family="DBOzoneX-Bold" font-weight="bold" font-size="16pt" color=" #000000">
                                             <fo:inline>เงื่อนไขในการให้ความยินยอมเปิดเผยข้อมูลผ่านระบบอินเทอร์เน็ต</fo:inline>
                                          </fo:block>
                                       </fo:block-container>
                                    </fo:table-cell>
                                 </fo:table-row>
                                 
                                 <fo:table-row>
                                    <fo:table-cell text-align="justify">
                                       <fo:block-container width="550px" height="40px" margin-left="5px" margin-top="20px">
                                          <fo:block text-align="left" font-family="DBOzoneX" font-size="14pt" font-weight="normal" color=" #000000">
                                             <fo:inline >&#160;&#160;&#160;&#160;&#160; ข้าพเจ้าทราบดีว่า การให้ความยินยอมผ่านระบบอินเทอร์เน็ตจะมีลักษณะเป็น “ข้อมูลอิเล็กทรอนิกส์” และเป็นข้อความที่ได้สร้าง ส่ง รับ เก็บรักษาหรือประมวลผลด้วยวิธีทางอิเล็กทรอนิกส์ซึ่งจะมีผลเป็นการให้ความยินยอมในการเปิดเผยหรือใช้ข้อมูลของข้าพเจ้า ตามกฎหมายว่าด้วยการประกอบธุรกิจข้อมูลเครดิตและข้าพเจ้าจะไม่ยกเลิกเพิกถอนหรือปฏิเสธความยินยอมนี้ เพราะเหตุที่เป็นข้อ มูลอิเล็กทรอนิกส์</fo:inline>
                                          </fo:block>
                                       </fo:block-container>
                                    </fo:table-cell>
                                 </fo:table-row>
                                 
                                 <fo:table-row padding="3pt">
                                    <fo:table-cell text-align="justify">
                                       <fo:block-container width="550px" height="50px" margin-left="5px" margin-top="40px">
                                          <fo:block text-align="left" font-family="DBOzoneX" font-size="14pt" font-weight="normal" color=" #000000">
                                             <fo:inline>&#160;&#160;&#160;&#160;&#160; ความยินยอมนี้จัดทำขึ้นด้วยความสมัครใจของข้าพเจ้าและส่งผ่านระบบอินเทอร์เน็ตให้แก่ บริษัท ข้อมูลเครดิตแห่งชาติ จำกัด (บริษัท) เพื่อเป็นหลักฐานว่า ข้าพเจ้าตกลงยินยอมให้บริษัท ข้อมูลเครดิตแห่งชาติ จำกัด (บริษัท) เปิดเผยหรือให้ข้อมูลของข้าพเจ้าแก่</fo:inline>
                                             <fo:inline font-family="DBOzoneX-Bold" font-size="14pt" font-weight="bold"> ธนาคารทหารไทยธนชาต จำกัด (มหาชน) (TMBThanachart) </fo:inline>
                                             <fo:inline>ซึ่งเป็นสมาชิกหรือผู้ใช้บริการของบริษัท เพื่อประโยชน์ในการวิเคราะห์สินเชื่อ การออกบัตรเครดิต ตามคำขอสินเชื่อ/ขอออกบัตรเครดิตของข้าพเจ้าที่ให้ไว้กับธนาคารดังกล่าวข้างต้น รวมทั้งเพื่อประโยชน์ในการทบทวนสินเชื่อ ต่ออายุสัญญาสินเชื่อ/บัตรเครดิต การบริหารและป้องกันความเสี่ยงตามข้อกำหนดของธนาคารแห่งประเทศไทย และให้ถือว่า ความยินยอมเปิดเผยข้อมูลที่ทำขึ้นผ่านระบบอินเทอร์เน็ตนี้ เมื่อประมวลผลและจัดพิมพ์ขึ้นจากข้อมูลอิเล็กทรอนิกส์แล้ว ไม่ว่าในรูปแบบใดๆ เป็นหลักฐานในการให้ความยินยอมด้วยตนเองของข้าพเจ้าเช่นเดียวกัน</fo:inline>
                                          </fo:block>
                                       </fo:block-container>
                                    </fo:table-cell>
                                 </fo:table-row>
                                 
                                 <fo:table-row padding="3pt">
                                    <fo:table-cell text-align="justify">
                                       <fo:block-container width="550px" height="15px" margin-left="5px" margin-top="100px">
                                          <fo:block text-align="left" font-family="DBOzoneX" font-size="14pt" font-weight="normal" color=" #000000">
                                             <fo:inline>&#160;&#160;&#160;&#160;&#160; อนึ่ง ก่อนให้ความยินยอม ข้าพเจ้าได้ทราบถึงวิธีการและเงื่อนไขของวิธีการให้ความยินยอมในการเปิดเผยหรือให้ข้อมูลผ่านระบบ อินเทอร์เน็ต ซึ่งระบุไว้ด้านบนของความยินยอมนี้อย่างชัดเจนแล้ว</fo:inline>
                                          </fo:block>
                                       </fo:block-container>
                                    </fo:table-cell>
                                 </fo:table-row>
                                 
                                 <fo:table-row>
                                    <fo:table-cell text-align="justify">
                                       <fo:block-container width="550px" height="60px" margin-left="5px" margin-top="30px">
                                          <fo:block text-align="left" font-family="DBOzoneX" font-size="14pt" font-weight="normal" color=" #000000">
                                             <fo:inline font-family="DBOzoneX-Bold" font-size="14pt" font-weight="bold" text-decoration="underline">หมายเหตุ:</fo:inline>
                                             <fo:inline>&#160;ข้อมูลที่บริษัท ข้อมูลเครดิตแห่งชาติ จำกัด เปิดเผยให้แก่สถาบันการเงินที่เป็นสมาชิกหรือผู้ใช้บริการ เป็นเพียงองค์ประกอบ หนึ่งในการพิจารณาสินเชื่อของสถาบันการเงิน แต่การเปิดเผยข้อมูลดังกล่าวเป็นสิทธิของเจ้าของข้อมูลที่จะให้ความยินยอมหรือไม่ก็ได้</fo:inline>
                                          </fo:block>
                                       </fo:block-container>
                                    </fo:table-cell>
                                 </fo:table-row>
                                 
                              </fo:table-body>
                           </fo:table>
                        </fo:table-cell>
                     </fo:table-row>
                  </fo:table-body>
               </fo:table>
               <fo:block id="lastpagenum" />
            </fo:flow>
         </fo:page-sequence>
      </fo:root>
   </xsl:template>
</xsl:stylesheet>