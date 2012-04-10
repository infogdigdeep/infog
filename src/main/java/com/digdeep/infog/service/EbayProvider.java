package com.digdeep.infog.service;

import java.util.List;

import javax.xml.ws.BindingProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ebay.apis.eblbasecomponents.FindPopularItemsRequestType;
import ebay.apis.eblbasecomponents.FindPopularItemsResponseType;
import ebay.apis.eblbasecomponents.Shopping;
import ebay.apis.eblbasecomponents.ShoppingInterface;
import ebay.apis.eblbasecomponents.SimpleItemArrayType;
import ebay.apis.eblbasecomponents.SimpleItemType;

public class EbayProvider {
	private static Logger logger = LoggerFactory.getLogger(EbayProvider.class);

	public List<SimpleItemType> getEbayItems(String searchItem) {
		ShoppingInterface port = new Shopping().getShopping();
		BindingProvider bp = (BindingProvider) port;
		String ebayURL = (String)bp.getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
		logger.debug("Connecting to Ebay: " + ebayURL);
        // add eBay-required parameters to the URL
        String endpointURL = ebayURL + "?callname=FindPopularItems&siteid=0"
              + "&appid=????"
              + "&version=725&requestencoding=SOAP";

        // replace the endpoint address with the new value
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
              endpointURL);

        FindPopularItemsRequestType req = new FindPopularItemsRequestType();
        req.setQueryKeywords(searchItem);

        FindPopularItemsResponseType resp = port.findPopularItems(req);
        SimpleItemArrayType siat = resp.getItemArray();
        List<SimpleItemType> lsit = siat.getItem();

        return lsit;
	}
	
	public static void main (String [] args) {
		EbayProvider prov = new EbayProvider();
		List<SimpleItemType> typeList = prov.getEbayItems("ipad");
	}
}
