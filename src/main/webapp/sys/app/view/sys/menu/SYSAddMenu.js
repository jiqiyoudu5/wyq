Ext.define('EJ.view.sys.menu.SYSAddMenu', {
	extend : 'Ext.window.Window',
	alias : 'widget.sysAddMenu',
	title : '新增菜单',
	layout : 'fit',
	width : 300,
	modal : true,
	resizable : false,
	initComponent : function() {
		var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
		this.items = [{
			xtype : 'form',
			border : false,
			bodyPadding : '5 5 0',
			items : [{
				xtype : 'container',
				anchor : '100%',
				layout : 'hbox',
				items : [{
					xtype : 'container',
					flex : 1,
					layout : 'anchor',
					items : [{
								xtype : 'textfield',
								name : 'id',
								hidden : true
							}, {
								xtype : 'textfield',
								fieldLabel : '菜单名称',
								allowBlank : false,
								name : 'text',
								afterLabelTextTpl : required,
								labelWidth : 90,
								anchor : '100%'
							}, {
								xtype : 'combo',
								name : 'leaf',
								fieldLabel : '节点类型',
								displayField : 'text',
								valueField : 'value',
								editable : false,
								value : false,
								store : Ext.create('Ext.data.Store', {
											fields : ['text', 'value'],
											data : [{
														'text' : '子节点',
														'value' : true
													}, {
														'text' : '根节点',
														'value' : false
													}]
										}),
								labelWidth : 90,
								anchor : '100%'
							}, {
								xtype : 'combo',
								fieldLabel : '菜单图标',
								name : 'icon',
								displayField : 'name',
								valueField : 'url',
								store : 'sys.file.SYSMenuIcon',
								queryMode : 'remote',// 远程加载数据
								queryParam : 'name',// 过滤条件
								minChars : 1,// 录入一个字符加远程过滤
								typeAhead : true,// 列出符合条件字符
								forceSelection : true,// 必须是列表中的才可以选
								listConfig : {
									getInnerTpl : function() {
										// return '<img
										// src="file/img/{url}"/>&nbsp;{text}';
										return '<div data-qtip ="<img src=file/img/{url} width=50px height=50px/>">{name}</div>';
									}
								},
								labelWidth : 90,
								anchor : '100%'
							}, {
								xtype : 'textfield',
								fieldLabel : '菜单指向',
								name : 'value',
								labelWidth : 90,
								anchor : '100%'
							}, {
								xtype : 'combo',
								name : 'expanded',
								fieldLabel : '默认展开',
								displayField : 'text',
								valueField : 'value',
								editable : false,
								value : true,
								store : Ext.create('Ext.data.Store', {
											fields : ['text', 'value'],
											data : [{
														'text' : '是',
														'value' : true
													}, {
														'text' : '否',
														'value' : false
													}]
										}),
								labelWidth : 90,
								anchor : '100%'
							}, {
								xtype : 'textfield',
								fieldLabel : '排序号',
								name : 'orderValue',
								labelWidth : 90,
								anchor : '100%'
							}, {
								xtype : 'textarea',
								name : 'remark',
								fieldLabel : '备注(字符≦100)',
								height : 60,
								maxLength : 100,
								anchor : '100%'
							}]
				}]
			}],
			buttons : [{
						text : '提交',
						formBind : true,
						action : 'add-treeMenu'
					}, {
						text : '取消',
						scope : this,
						handler : this.close
					}]
		}];
		this.callParent(arguments);
	}
});
