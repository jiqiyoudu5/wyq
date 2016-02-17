Ext.define('EJ.view.sys.dictionary.UpdateDicTypeWin', {
	extend : 'Ext.window.Window',
	alias : 'widget.updateDicTypeWin',
	title : '修改字典类别',
	layout : 'fit',
	width : 300,
	modal : true,
	resizable : false,
	autoShow : true,
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
														allowBlank : false,
														labelWidth : 90,
														anchor : '100%'
													}, {
														xtype : 'textfield',
														fieldLabel : 'type',
														name : 'type',
														emptyText : '不填或原值时则不修改此项',
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
								action : 'update'
							}, {
								text : '取消',
								scope : this,
								handler : this.close
							}]
				}];
		this.callParent(arguments);
	}
});