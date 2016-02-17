Ext.define('EJ.view.sys.roles.EditRoleWin', {
	extend : 'Ext.window.Window',
	alias : 'widget.editRoleWin',
	title : '修改角色信息',
	layout : 'fit',
	width : 300,
	modal : true,
	resizable : false,
	autoShow : true,
	initComponent : function() {
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
														fieldLabel : '角色名称',
														name : 'name',
														labelWidth : 90,
														anchor : '100%'
													}, {
														xtype : 'textfield',
														fieldLabel : 'CODE',
														name : 'code',
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
								action : 'update-role'
							}, {
								text : '取消',
								scope : this,
								handler : this.close
							}]
				}];
		this.callParent(arguments);
	}
});