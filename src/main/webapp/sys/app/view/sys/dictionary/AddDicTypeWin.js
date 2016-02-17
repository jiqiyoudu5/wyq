Ext.define('EJ.view.sys.dictionary.AddDicTypeWin', {
	extend : 'Ext.window.Window',
	alias : 'widget.addSYSDicTypeWin',
	title : '新增字典类别',
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
														fieldLabel : '字典名称',
														name : 'text',
														afterLabelTextTpl : required,
														labelWidth : 90,
														allowBlank : false,
														anchor : '100%'
													}, {
														xtype : 'textfield',
														fieldLabel : '字典类型',
														name : 'type',
														afterLabelTextTpl : required,
														allowBlank : false,
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