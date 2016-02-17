Ext.define('EJ.view.sys.files.UploadWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.uploadWin',
	title : '上传附件',
	layout : 'fit',
	width : 350,
	modal : true,
	resizable : false,
	autoShow : true,
	initComponent : function() {
		var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
		this.items = [{
					xtype : 'form',
					border : false,
					bodyPadding : '10 10 10',
					defaults : {
						anchor : '100%',
						msgTarget : 'side',
						labelWidth : 50
					},
					items : [{
						xtype : 'filefield',
						fieldLabel : '附件',
						name : 'attached',
						allowBlank : false,
						afterLabelTextTpl : required
							// buttonText : 'Select File...'
						}/*
							 * , { xtype : 'filefield', name : 'attached',
							 * fieldLabel : '附件' // buttonText : 'Select
							 * Photo...' }
							 */, {
						xtype : 'textfield',
						fieldLabel : '重命名',
						name : 'newName'
					}, {
						fieldLabel : "类型",
						afterLabelTextTpl : required,
						xtype : "combo",
						name : 'type',
						displayField : 'text',// 显示值
						valueField : 'id',// 显示值在Store中对应的id值
						editable : true,
						store : "sys.file.SYSDicFileType",
						queryMode : 'remote',// 远程加载数据
						queryParam : 'text',// 过滤条件
						minChars : 1,// 录入一个字符加远程过滤
						typeAhead : true,// 列出符合条件字符
						forceSelection : true,// 必须是列表中的才可以选
						allowBlank : false
					}],
					buttons : [{
								text : '提交',
								icon : './resources/img/add.png',
								formBind : true,
								action : 'add'
							}, {
								text : '取消',
								scope : this,
								handler : this.close
							}]
				}];

		this.callParent(arguments);
	}
});