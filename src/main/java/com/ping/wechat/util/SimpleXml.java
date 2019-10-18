/**
 * <h1>简单的XML元素类</h1>
 * 由XMLReader。read方法创建
 * https://github.com/shmilyhe/SimpleXml.git
 * 使用本类时请保留类的说明和来源
 * 性能测试：
 * 单线程 100000 次解析4层约100个元素的XML 6708.26TPS
 * 单线程操作 dom节点 152万 TPS
 * <p>
 * 提供简单的XML操作方法
 * <root>
 * <books gropName="newbook">
 * <book name="book1"/>
 * <book name="book2"/>
 * <book name="book3"/>
 * <book>
 * <name>book4</name>
 * </book>
 * </books>
 * </root>
 * 1\获取 books的gropName属性
 * SimpleXml.read(xml,"utf-8").g("root").g("books").g("gropName").getText();
 * 或
 * SimpleXml.read(xml,"utf-8").g("root").g("books").getAttribute("gropName");
 * <p>
 * 遍历所有的BOOK
 * <p>
 * SimpleXml.read(xml,"utf-8").g("root").g("books").g("book").each(new Ieach(){
 * public void each(int index,XMLElement el){
 * 打印name属性
 * System.out.println(el.g("name").getText());
 * }
 * });
 */

package com.ping.wechat.util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class SimpleXml extends DefaultHandler {

	/**********************************************************************
	 *                             XML解析相关                                                                                                  *
	 **********************************************************************/
	private Stack<SimpleXml> tagNameStack = new Stack<SimpleXml>();
	private SimpleXml retEl;
	private StringBuilder sb;
	static SAXParserFactory factory = SAXParserFactory.newInstance();

	public static SimpleXml read(File file) throws IOException {
		FileInputStream finp = null;
		try {
			finp = new FileInputStream(file);
			SimpleXml el = new SimpleXml().readFrom(finp);
			el.setRoot(true);
			return el;
		} catch (IOException e) {
			return new SimpleXml();
		} finally {
			if (finp != null) {
				finp.close();
			}
		}
	}

	/**
	 * 从InputStream输入流中读取
	 * @param inp InputStream
	 * @return SimpleXml对象
	 */
	public SimpleXml readFrom(InputStream inp) {
		this.isRoot = true;
		//SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			parser.parse(inp, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.addSub(retEl);
		return this;
	}

	/**
	 * 从XML字符串中读取
	 * @param xml XML字符串
	 * @return SimpleXml对象
	 */
	public SimpleXml readFrom(String xml) {
		try {
			this.isRoot = true;

			ByteArrayInputStream bdinp;
			bdinp = new ByteArrayInputStream(xml.getBytes());

			return new SimpleXml().readFrom(bdinp);

		} catch (Exception e) {

			e.printStackTrace();
			return new SimpleXml();
		}

	}

	/**
	 * 从XML字符串中读取
	 * @param xml XML字符串
	 * @param encoding 编码
	 * @return SimpleXml对象
	 */
	public static SimpleXml read(String xml, String encoding) {
		ByteArrayInputStream bdinp;

		try {
			bdinp = new ByteArrayInputStream(xml.getBytes(encoding));

			return new SimpleXml().readFrom(bdinp);

		} catch (UnsupportedEncodingException e) {

			return new SimpleXml().readFrom(xml);


		} catch (Exception e) {


			e.printStackTrace();

			return new SimpleXml();

		}

	}

	/**
	 * 从XML字符串中读取
	 * @param xml xml字符 串
	 * @return
	 */
	public static SimpleXml read(String xml) {
		try {
			return new SimpleXml().readFrom(xml);
		} catch (Exception e) {
			e.printStackTrace();
			return new SimpleXml();
		}

	}

	@Override
	public void startElement(String uri, String localName, String qName,
							 Attributes attributes) throws SAXException {
		/**
		 * 上一层的元素
		 */
		SimpleXml pre = tagNameStack.empty() ? null : tagNameStack.peek();

		SimpleXml cur = new SimpleXml();
		cur.setQname(qName);
		cur.setLocalName(localName);
		if (retEl == null) {
			retEl = cur;
		}
		tagNameStack.push(cur);
		if (pre != null) {
			pre.addSub(cur);
		}
		int len = attributes.getLength();
		for (int i = 0; i < len; i++) {
			cur.setAttribute(attributes.getQName(i), attributes.getValue(i));
		}
		sb = new StringBuilder();
		//System.out.println(""+qName+"开始!上一个元素:"+preName);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (!tagNameStack.empty()) {
			SimpleXml e = tagNameStack.pop();
			if (sb != null && sb.length() > 0) {
				e.setText(sb.toString());
			}
		}

		sb = new StringBuilder();
		//System.out.println();

	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		//tagNameStack.peek();

		for (int i = 0, j = start; i < length; i++, j++) {
			sb.append(ch[j]);
		}
		/*String str =tagNameStack.peek().getText();
		if(str!=null)str+=new String(ch,start,length);
		else str =new String(ch,start,length);
		tagNameStack.peek().setText(str);//(key, value)
*/
	}

	@Override
	public void error(SAXParseException e) throws SAXException {
		super.error(e);
	}


	/**********************************************************************
	 *                             XML节点属性和方法                                                                                                  *
	 **********************************************************************/
	/**
	 * 文本节点
	 */
	public final int TYPE_TEXT = 1;

	/**
	 * 属性节点
	 */
	public final int TYPE_ATT = 2;

	/**
	 * 标签
	 */
	public final int TYPE_TAG = 3;

	/**
	 * 文档
	 */
	public final int TYPE_DOC = 4;

	/**
	 * 标签名
	 */
	private String qName;

	/**
	 * 标签的文本
	 */
	private String text;

	/**
	 * 标签的URI
	 */
	private String uri;

	private String localName;

	/**
	 * 标签的类型
	 */
	private int type;

	/**
	 * 属性
	 */
	private HashMap<String, String> attributes;

	/**
	 * 子节点
	 */
	private List<SimpleXml> subElements;

	/**
	 * 选择器选中的同一层次的下一个元素
	 */
	private SimpleXml next;

	/**
	 * parent node
	 */
	private SimpleXml parent;

	private int subIndex;

	private boolean isRoot;

	private String encoding = "utf-8";

	/**
	 * 附加信息
	 */
	private HashMap extraMap;


	/**
	 * 添加附加信息
	 * @param key
	 * @param value
	 */
	public void addExtra(String key, Object value) {
		if (extraMap == null) extraMap = new HashMap();
		extraMap.put(key, value);
	}


	/**
	 * 获取附加信息
	 * @param key
	 * @return
	 */
	public Object getExtra(String key) {
		if (extraMap == null) return null;
		return extraMap.get(key);
	}

	/**
	 * 创建根节点
	 * @return
	 */
	public static SimpleXml createRoot() {
		SimpleXml e = new SimpleXml();
		e.isRoot = true;
		return e;
	}

	/**
	 * 创建一个节点
	 * @param qname 节点名称
	 * @return
	 */
	public static SimpleXml createTag(String qname) {
		return createTag(qname, null);
	}

	/**
	 * 创建一个节点
	 * @param qname 节点名称
	 * @param text 文本内容
	 * @return
	 */
	public static SimpleXml createTag(String qname, String text) {
		SimpleXml e = new SimpleXml();
		e.qName = qname;
		e.text = text;
		return e;
	}

	/**
	 * 获取父节点
	 * @return
	 */
	public SimpleXml getParent() {
		return parent;
	}

	/**
	 * 设置父节点
	 * @param parent
	 */
	public void setParent(SimpleXml parent) {
		this.parent = parent;
	}

	/**
	 * 设置节点名称
	 * @param qName 节点名称
	 */
	public void setQname(String qName) {
		this.qName = qName;
	}

	/**
	 * 设置TEXT 内容
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * 获得节点的类型
	 * @return
	 */
	public int getType() {
		return type;
	}

	/**
	 * 获取节点名称
	 * @return 节点名称
	 */
	public String getQname() {
		return qName;
	}

	public SimpleXml() {
	}

	public SimpleXml(String uri, String qname, String text) {
		this.uri = uri;
		this.qName = qname;
		this.text = text;
		type = TYPE_TAG;
		System.out.println("init:" + qname);
	}

	public SimpleXml(String qname, String text) {
		this.qName = qname;
		this.text = text;
		type = TYPE_TAG;
		System.out.println("init:" + qname);
	}


	/**
	 * 增加属性
	 * @param key
	 * @param value
	 */
	public void setAttribute(String key, String value) {
		if (attributes == null) {
			attributes = new HashMap<String, String>();
		}
		attributes.put(key, value);
	}

	/**
	 * 属性个数
	 * @return
	 */
	public int getAttributeCount() {
		if (attributes == null) {
			return 0;
		}
		return attributes.size();
	}

	public String[] getAttributeNames() {
		if (attributes == null) {
			return null;
		}
		Set<String> keySet = attributes.keySet();
		String[] anames = new String[keySet.size()];
		keySet.toArray(anames);
		return anames;
	}

	public Set<String> getAttributeNamesSet() {
		if (attributes == null) return new HashSet<String>();
		return attributes.keySet();
	}


	/**
	 * 增加子元素
	 * @param el
	 */
	public void addSub(SimpleXml el) {
		if (el == null) {
			return;
		}
		if (el.isRoot()) {
			el = el.g();
		}
		if (subElements == null) {
			subElements = new ArrayList<SimpleXml>();
		}
		el.setSubIndex(subElements.size());
		subElements.add(el);
		el.setParent(this);
	}

	/**
	 * 获取属性
	 * @param aname
	 * @return
	 */
	public String getAttribute(String aname) {
		if (attributes != null) {
			return attributes.get(aname);
		}
		return null;
	}

	/**
	 * 获取文本
	 * @return
	 */
	public String getText() {
		if (text == null) {
			return "";
		}
		return text;
	}

	/**
	 * 获取URI
	 * @return
	 */
	public String getUri() {
		return uri;
	}

	public int getSubCount() {
		if (subElements == null) {
			return 0;
		}
		return subElements.size();
	}

	public SimpleXml getSubElement(int index) {
		if (subElements == null) {
			return null;
		}
		if (index >= subElements.size()) {
			return null;
		}
		return subElements.get(index);
	}

	/**********************************************************************
	 *                             XML操作相关方法                                                                                                  *
	 **********************************************************************/

	/**
	 * 获取下一层的节点
	 * @return
	 */
	public SimpleXml g() {
		SimpleXml curr = this;
		SimpleXml head = null;
		SimpleXml flag = null;
		//System.out.println(name);
		while (curr != null) {
			/**
			 * 查找子节点
			 *
			 */
			if (curr.subElements != null) {
				for (SimpleXml el : curr.subElements) {
					if (head == null) {
						head = el;
						flag = el;
					} else {
						flag.next = el;
						flag = el;
					}

				}
			}

			/**
			 * 从同级的元素中找
			 */
			curr = curr.next;
		}
		return head == null ? new SimpleXml() : head;
	}

	/**
	 *
	 * @return
	 */
	public SimpleXml gs() {
		SimpleXml curr = this;
		SimpleXml head = null;
		SimpleXml flag = null;

		/**
		 * 查找子节点
		 *
		 */
		if (curr.subElements != null) {
			for (SimpleXml el : curr.subElements) {
				if (head == null) {
					head = el;
					flag = el;
				} else {
					flag.next = el;
					flag = el;
				}

			}
		}

		return head == null ? new SimpleXml() : head;
	}

	public List<SimpleXml> getSubElements() {
		return subElements;
	}

	public List<SimpleXml> find(String qName) {
		if (subElements == null || subElements.size() == 0) {
			return null;
		}
		List<SimpleXml> xmlList = new LinkedList<SimpleXml>();
		for (SimpleXml xml : this.subElements) {
			if (qName.equals(xml.qName)) {
				xmlList.add(xml);
			} else {
				List<SimpleXml> elList = xml.find(qName);
				if (elList != null) {
					xmlList.addAll(elList);
				}
			}
		}
		return xmlList;
	}

	public SimpleXml select(String path) {
		String[] paths = path.split("[\\.]");
		SimpleXml el = this;
		for (String p : paths) {
			el = el.gs(p);
		}
		//this.gs(name)
		return el;
	}


	/**
	 * 选择器
	 * @param name
	 * @return
	 */
	public SimpleXml g(String name) {
		SimpleXml curr = this;
		SimpleXml head = null;
		SimpleXml flag = null;
		//System.out.println(name);
		while (curr != null) {
			//System.out.println(index++);
			SimpleXml rst = null;


			/**
			 * 从属性中查找
			 */
			if (curr.attributes != null && curr.attributes.containsKey(name)) {
				rst = new SimpleXml();
				rst.text = curr.attributes.get(name);
				rst.type = TYPE_ATT;
			}


			if (rst != null) {
				if (head == null) {
					head = rst;
					flag = rst;
				} else {
					flag.next = rst;
					flag = rst;
				}
			}
			rst = null;


			/**
			 * 查找子节点
			 *
			 */
			if (curr.subElements != null) {
				for (SimpleXml el : curr.subElements) {
					if (name.equals(el.qName)) {
						//System.out.println(el);
						if (head == null) {
							head = el;
							flag = el;
						} else {
							flag.next = el;
							flag = el;
						}
					}
				}
			}

			/**
			 * 从同级的元素中找
			 */
			curr = curr.next;
		}
		return head == null ? new SimpleXml() : head;
	}

	/**
	 * 选择器 ,只先体身的
	 * @param name
	 * @return
	 */
	public SimpleXml gs(String name) {
		SimpleXml curr = this;
		SimpleXml head = null;
		SimpleXml flag = null;
		//System.out.println(name);
		SimpleXml rst = null;


		/**
		 * 从属性中查找 
		 */
		if (curr.attributes != null && curr.attributes.containsKey(name)) {
			rst = new SimpleXml();
			rst.text = curr.attributes.get(name);
			rst.type = TYPE_ATT;
		}


		if (rst != null) {
			if (head == null) {
				head = rst;
				flag = rst;
			} else {
				flag.next = rst;
				flag = rst;
			}
		}
		rst = null;


		/**
		 * 查找子节点
		 *
		 */
		if (curr.subElements != null) {
			for (SimpleXml el : curr.subElements) {
				if (name.equals(el.qName)) {
					//System.out.println(el);
					if (head == null) {
						head = el;
						flag = el;
					} else {
						flag.next = el;
						flag = el;
					}
				}
			}
		}


		return head == null ? new SimpleXml() : head;
	}


	/**
	 *
	 * @param qname
	 * @return
	 */
	public List<SimpleXml> sub(String qname) {
		ArrayList<SimpleXml> list = new ArrayList<SimpleXml>();
		if (this.subElements != null) {
			for (SimpleXml el : this.subElements) {
				if (qname.equals(el.qName)) {
					list.add(el);
				}
			}
		}
		return list;
	}


	/**
	 * 迭代器
	 * @param each
	 */
	public void each(Ieach each) {
		SimpleXml flag = this;
		int index = 0;
		do {
			each.each(index, flag);
			index++;
			flag = flag.next;
		} while (flag != null);
	}

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}


	/**
	 * 迭代器
	 * @param each
	 */
	public void eachContent(Ieach each) {
		SimpleXml flag = this;
		int index = 0;
		if (this.attributes != null) {
			for (String str : this.attributes.keySet()) {
				SimpleXml rst = new SimpleXml();
				rst.text = this.attributes.get(str);
				rst.type = TYPE_ATT;
				rst.qName = str;
				each.each(index, rst);
				index++;
			}
		}
		if (this.subElements != null) {
			for (SimpleXml el : this.subElements) {
				each.each(index, el);
				index++;
			}
		}
	}


	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof SimpleXml) {
			SimpleXml el = (SimpleXml) arg0;
			if (this == el) return true;
			return c(this, el);
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (this.qName == null) return 0;
		return this.qName.hashCode();
	}

	/**
	 * 判断本节点名与属性是否完全一样
	 * @param el
	 * @return
	 */
	public boolean _equals(SimpleXml el) {
		Set<String> leftSet = this.getAttributeNamesSet();
		Set<String> rightSet = el.getAttributeNamesSet();
		if (leftSet.size() != rightSet.size()) {
			return false;
		}
		for (String aname : leftSet) {
			String valueL = this.getAttribute(aname);
			String valueR = el.getAttribute(aname);
			if (valueR == null) {
				return false;
			} else {
				if (!_quals(valueL, valueR)) {
					return false;
				}

			}
		}
		return true;
	}

	private boolean c(SimpleXml left, SimpleXml right) {
		if (!_quals(left.qName, right.qName)) {
			return false;
		}
		/**
		 * 比较attribute
		 */
		Set<String> leftSet = left.getAttributeNamesSet();
		Set<String> rightSet = right.getAttributeNamesSet();
		if (leftSet.size() != rightSet.size()) {
			return false;
		}
		for (String aname : leftSet) {
			String valueL = left.getAttribute(aname);
			String valueR = right.getAttribute(aname);
			if (valueR == null) {
				return false;
			} else {
				if (!_quals(valueL, valueR)) {
					return false;
				}

			}
		}

		//比较TEXT
		String leftText = left.getText();
		String rightText = right.getText();
		if (!_quals(leftText, rightText)) {
			return false;
		}

		/**
		 * 比较子节点
		 *
		 */
		if (left.getSubCount() != right.getSubCount()) {
			return false;
		}
		if (left.getSubCount() != right.getSubCount()) {
			return false;
		}

		/**
		 * 左与右对比
		 */
		HashSet tmpSet = new HashSet();
		for (int i = 0; i < left.getSubCount(); i++) {
			SimpleXml el = left.getSubElement(i);
			SimpleXml e = right.getSubElement(i);
			if (!c(el, e)) {
				return false;
			}

		}
		return true;

	}

	public String escapeXml(String str) {
		if (str == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		int len = str.length();
		for (int i = 0; i < len; i++) {
			char c = str.charAt(i);
			switch (c) {
				case '<':
					sb.append("&lt;");
					break;
				case '>':
					sb.append("&gt;");
					break;
				case '"':
					sb.append("&quot;");
					break;
				case '&':
					sb.append("&amp;");
					break;
				case '\'':
					sb.append("&apos;");
					break;
				default:
					sb.append(c);
			}
		}
		return sb.toString();
	}

	public void toString(StringBuffer sb, SimpleXml e, int level) {
		StringBuffer blank = new StringBuffer();
		/**
		 * 处理头
		 */
		if (e.isRoot()) {
			sb.append("<?xml version=\"1.0\" encoding=\"" + this.encoding + "\" ?>\r\n");

			if (e.subElements != null && e.subElements.size() > 0) {
				for (SimpleXml sub : e.subElements) {
					toString(sb, sub, level + 1);
				}
			}
			return;
		}
		/**
		 * 处理头结束
		 */
		for (int i = 0; i < level; i++) {
			blank.append("     ");
		}
		sb.append(blank).append("<").append(e.getQname());
		if (e.attributes != null && e.attributes.size() > 0) {
			Set<Entry<String, String>> entrySet = e.attributes.entrySet();
			for (Entry<String, String> entry : entrySet) {
				sb.append(" ").append(entry.getKey()).append("=\"").append(entry.getValue()).append("\" ");
			}
		}

		if (e.subElements != null && e.subElements.size() > 0) {
			sb.append(">\r\n");
			for (SimpleXml sub : e.subElements) {
				toString(sb, sub, level + 1);
			}
			sb.append(blank).append("</").append(e.getQname()).append(">\r\n");
		} else {
			if (e.getText() == null || "".equals(("" + e.getText()).trim())) {
				sb.append("/>\r\n");
			} else {
				String text = e.getText();

				if (text.indexOf('<') > -1 || text.indexOf('>') > -1 || text.indexOf('&') > -1 || text.indexOf('@') > -1) {
					//sb.append(">").append("<![CDATA[").append(text).append("]]>").append("</").append(e.getQname()).append(">\r\n");
					sb.append(">").append(escapeXml(text)).append("</").append(e.getQname()).append(">\r\n");
				} else {
					sb.append(">").append(text).append("</").append(e.getQname()).append(">\r\n");
				}


			}
		}


	}

	public boolean revome() {
		SimpleXml pel = this.getParent();
		this.parent = null;
		/*int i=0;
		boolean found=false;
		for(SimpleXml e:pel.subElements){
			if(this.equals(e)){found=true; break;}
			i++;
		}
		if(found)pel.subElements.remove(i);
		*/
		return pel.subElements.remove(this);
	}

	public boolean revome(SimpleXml xml) {
		List<SimpleXml> list = this.subElements;
		List<SimpleXml> subsublist = new LinkedList<SimpleXml>();
		while (true) {
			for (SimpleXml x : list) {
				if (x.subElements != null) {
					subsublist.addAll(x.subElements);
				}
				if (x.equals(xml)) {
					return x.revome();
				}
			}
			//list.clear();
			list = subsublist;
			subsublist = new LinkedList<SimpleXml>();
			if (list.size() < 1) {
				break;
			}
		}
		return false;
	}

	/**
	 *
	 * @param qName
	 * @return
	 */
	public SimpleXml addTag(String qName) {
		SimpleXml el = new SimpleXml();
		el.qName = qName;
		this.addSub(el);
		return el;
	}


	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		toString(sb, this, 0);
		return sb.toString();
	}

	/**
	 * 转JSON
	 */

	/**
	 *
	 * @return
	 */

	private Set exculdattr = null;

	public String toJsonStringExculd(Set exc) {
		exculdattr = exc;
		return toJsonString();
	}

	public String toJsonString() {
		StringBuffer sb = new StringBuffer();
		if (this.isRoot) {
			if (subElements != null && subElements.size() > 0) {
				SimpleXml sel = subElements.get(0);
				toJsonString(sb, sel, 0);
			}
		} else {
			toJsonString(sb, this, 0);
		}
		return sb.toString();
	}

	public void toJsonString(StringBuffer sb, SimpleXml e, int level) {
		/**
		 * 处理头
		 */
		sb.append("{");
		sb.append("\"name\":\"").append(e.getQname()).append("\"");

		if (e.attributes != null && e.attributes.size() > 0) {
			Set<Entry<String, String>> entrySet = e.attributes.entrySet();
			LinkedList<Entry> alist = new LinkedList<Entry>();
			for (Entry e3 : entrySet) {
				if (exculdattr == null || !exculdattr.contains(e3.getKey())) {
					alist.add(e3);
				}
			}
			Collections.sort(alist, new Comparator<Entry>() {
				@Override
				public int compare(Entry o1, Entry o2) {
					return ((String) o1.getKey()).compareTo((String) o2.getKey());
				}
			});
			sb.append(",\"attributes\":{");
			boolean isFirst = true;
			for (Entry<String, String> entry : alist) {
				if (isFirst) {
					isFirst = false;
				} else {
					sb.append(",");
				}
				String value = entry.getValue();
				if (value != null && value.indexOf('"') > -1) {
					value = value.replaceAll("\"", "\\\\\"");
				}
				sb.append(" \"").append(entry.getKey()).append("\":\"").append(value).append("\" ");
			}
			sb.append("}");
		}

		if (e.subElements != null && e.subElements.size() > 0) {
			Collections.sort(e.subElements, new Comparator<SimpleXml>() {
				@Override
				public int compare(SimpleXml o1, SimpleXml o2) {
					return o1.getQname().compareTo(o2.getQname());
				}
			});

			sb.append(",\"subElements\":[");
			boolean isFirst = true;
			for (SimpleXml sub : e.subElements) {
				if (isFirst) {
					isFirst = false;
				} else {
					sb.append(",");
				}
				toJsonString(sb, sub, level + 1);
			}
			sb.append("]");
		} else {
			String text = e.getText();
			if (text != null) {
				text = text.trim();
			}
			if (text != null && text.indexOf('"') > -1) {
				text = text.replaceAll("\"", "\\\\\"");
			}
			sb.append(",\"text\":\"").append(text).append("\"");
		}
		sb.append("}");

	}

	public boolean _quals(String a, String b) {
		a = ("" + a).trim();
		b = ("" + b).trim();
		return a.equals(b);
	}

	public int getSubIndex() {
		return subIndex;
	}

	public void setSubIndex(int subIndex) {
		this.subIndex = subIndex;
	}

	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * write to Stream
	 * @param out
	 * @throws IOException
	 */
	public void write(OutputStream out) throws IOException {
		String xml = this.toString();
		out.write(xml.getBytes(this.encoding));
	}

	/**
	 * xml to byte for transfer
	 * @return
	 */
	public byte[] getXmlAsBytes() {
		String xml = this.toString();
		try {
			return xml.getBytes(this.encoding);
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
			return null;
		}
	}

	public void writeFile(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		write(fos);
		fos.flush();
		fos.close();
	}


	/**
	 * 迭代器
	 * @author ZhangJun 2017年11月23日
	 * @version 1.0
	 *
	 */
	public interface Ieach {
		void each(int index, SimpleXml el);
	}
}