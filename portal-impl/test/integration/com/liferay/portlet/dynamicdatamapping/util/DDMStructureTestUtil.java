/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Eudaldo Alonso
 */
public class DDMStructureTestUtil {

	public static DDMStructure addStructure(long groupId, String className)
		throws Exception {

		return addStructure(
			groupId, className, 0, getSampleStructureXSD(),
			LocaleUtil.getSiteDefault(), ServiceTestUtil.getServiceContext());
	}

	public static DDMStructure addStructure(
			long groupId, String className, Locale defaultLocale)
		throws Exception {

		return addStructure(
			groupId, className, 0, getSampleStructureXSD(), defaultLocale,
			ServiceTestUtil.getServiceContext());
	}

	public static DDMStructure addStructure(
			long groupId, String className, long parentStructureId)
		throws Exception {

		return addStructure(
			groupId, className, parentStructureId, getSampleStructureXSD(),
			LocaleUtil.getSiteDefault(), ServiceTestUtil.getServiceContext());
	}

	public static DDMStructure addStructure(
			long groupId, String className, long parentStructureId, String xsd,
			Locale defaultLocale, ServiceContext serviceContext)
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(defaultLocale, "Test Structure");

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return DDMStructureLocalServiceUtil.addStructure(
			TestPropsValues.getUserId(), groupId, parentStructureId,
			PortalUtil.getClassNameId(className), null, nameMap, null, xsd,
			PropsValues.DYNAMIC_DATA_LISTS_STORAGE_TYPE,
			DDMStructureConstants.TYPE_DEFAULT, serviceContext);
	}

	public static DDMStructure addStructure(
			long groupId, String className, String xsd)
		throws Exception {

		return addStructure(
			groupId, className, 0, xsd, LocaleUtil.getSiteDefault(),
			ServiceTestUtil.getServiceContext());
	}

	public static DDMStructure addStructure(String className) throws Exception {
		return addStructure(
			TestPropsValues.getGroupId(), className, 0, getSampleStructureXSD(),
			LocaleUtil.getSiteDefault(), ServiceTestUtil.getServiceContext());
	}

	public static DDMStructure addStructure(
			String className, Locale defaultLocale)
		throws Exception {

		return addStructure(
			TestPropsValues.getGroupId(), className, 0, getSampleStructureXSD(),
			defaultLocale, ServiceTestUtil.getServiceContext());
	}

	public static DDMStructure addStructure(String className, String xsd)
		throws Exception {

		return addStructure(
			TestPropsValues.getGroupId(), className, 0, xsd,
			LocaleUtil.getSiteDefault(), ServiceTestUtil.getServiceContext());
	}

	public static DDMStructure addStructure(
			String className, String xsd, Locale defaultLocale)
		throws Exception {

		return addStructure(
			TestPropsValues.getGroupId(), className, 0, xsd, defaultLocale,
			ServiceTestUtil.getServiceContext());
	}

	public static String getSampleStructuredContent() {
		return getSampleStructuredContent("name", "title");
	}

	public static String getSampleStructuredContent(
		String name, String keywords) {

		Document document = createDocumentContent();

		Element rootElement = document.getRootElement();

		Element dynamicElementElement = rootElement.addElement(
			"dynamic-element");

		dynamicElementElement.addAttribute("index-type", "keyword");
		dynamicElementElement.addAttribute("name", name);
		dynamicElementElement.addAttribute("type", "text");

		Element dynamicContentElement = dynamicElementElement.addElement(
			"dynamic-content");

		dynamicContentElement.addAttribute("language-id", "en_US");
		dynamicContentElement.addCDATA(keywords);

		return document.asXML();
	}

	public static String getSampleStructureXSD() {
		return getSampleStructureXSD("name");
	}

	public static String getSampleStructureXSD(String name) {
		Document document = createDocumentStructure();

		Element rootElement = document.getRootElement();

		Element dynamicElementElement = rootElement.addElement(
			"dynamic-element");

		dynamicElementElement.addAttribute(
			"autoGeneratedName", String.valueOf(true));
		dynamicElementElement.addAttribute("dataType", "string");
		dynamicElementElement.addAttribute("indexType", "text");
		dynamicElementElement.addAttribute("name", name);
		dynamicElementElement.addAttribute("type", "text");

		Element metaDataElement = dynamicElementElement.addElement("meta-data");

		metaDataElement.addAttribute("locale", "en_US");

		Element labelElement = metaDataElement.addElement("entry");

		labelElement.addAttribute("name", "label");
		labelElement.addCDATA("Field");

		Element requiredElement = metaDataElement.addElement("entry");

		requiredElement.addAttribute("name", "required");
		requiredElement.addCDATA("false");

		Element repeatableElement = metaDataElement.addElement("entry");

		repeatableElement.addAttribute("name", "repeatable");
		repeatableElement.addCDATA("true");

		return document.asXML();
	}

	protected static Document createDocumentContent() {
		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		rootElement.addAttribute("available-locales", "en_US");
		rootElement.addAttribute("default-locale", "en_US");
		rootElement.addElement("request");

		return document;
	}

	protected static Document createDocumentStructure() {
		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		rootElement.addAttribute("available-locales", "en_US");
		rootElement.addAttribute("default-locale", "en_US");

		return document;
	}

}