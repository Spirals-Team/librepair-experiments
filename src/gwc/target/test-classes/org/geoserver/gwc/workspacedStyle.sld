<?xml version="1.0" encoding="ISO-8859-1"?>
<StyledLayerDescriptor version="1.0.0"
xsi:schemaLocation="http://www.opengis.net/sld StyledLayerDescriptor.xsd"
xmlns="http://www.opengis.net/sld"
xmlns:ogc="http://www.opengis.net/ogc"
xmlns:xlink="http://www.w3.org/1999/xlink"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <NamedLayer>
    <Name>workspacedStyle</Name>
    <UserStyle>
      <Name>workspacedStyle</Name>
      <FeatureTypeStyle>
        <Rule>
           <MinScaleDenominator>100000</MinScaleDenominator>
           <MaxScaleDenominator>300000</MaxScaleDenominator>
           <FeatureTypeStyle>
             <Rule>
               <PointSymbolizer>
                 <Graphic>
                   <Mark>
                     <WellKnownName>circle</WellKnownName>
                     <Fill>
                       <CssParameter name="fill">#FF0000</CssParameter>
                     </Fill>
                   </Mark>
                   <Size>6</Size>
                 </Graphic>
               </PointSymbolizer>
             </Rule>
           </FeatureTypeStyle>
        </Rule>
      </FeatureTypeStyle>
    </UserStyle>
  </NamedLayer>
</StyledLayerDescriptor>