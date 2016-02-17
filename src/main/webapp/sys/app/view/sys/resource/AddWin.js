Ext.define('EJ.view.sys.resource.AddWin', {
	extend : 'Ext.window.Window',
	alias : 'widget.addWinResource',
	title : '新增资源信息',
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
														fieldLabel : '资源名称',
														name : 'name',
														afterLabelTextTpl : required,
														labelWidth : 90,
														allowBlank : false,
														anchor : '100%'
													}, {
														xtype : 'textfield',
														fieldLabel : 'URL',
														name : 'url',
														afterLabelTextTpl : required,
														labelWidth : 90,
														anchor : '100%'
													}, {
														xtype : 'textarea',
														name : 'remark',
														fieldLabel : '备注(字符≦200)',
														height : 60,
														maxLength : 200,
														anchor : '100%'
													}]
										}]
							}],
					buttons : [{
								text : '提交',
								formBind : true,
								action : 'add-resource'
							}, {
								text : '取消',
								scope : this,
								handler : this.close
							}]
				}];
		this.callParent(arguments);
	}
});