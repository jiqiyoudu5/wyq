Ext.define('EJ.view.sys.roles.RoleInfoWin', {
	extend : 'Ext.window.Window',
	alias : 'widget.roleInfoWin',
	title : '角色详细信息',
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
														fieldLabel : '资源名称',
														name : 'name',
														readOnly : true,
														anchor : '100%'
													}, {
														xtype : 'textfield',
														fieldLabel : 'CODE',
														name : 'code',
														readOnly : true,
														anchor : '100%'
													}, {
														xtype : 'textarea',
														name : 'remark',
														fieldLabel : '备注(字符≦100)',
														readOnly : true,
														height : 60,
														anchor : '100%'
													}]
										}]
							}]
				}];
		this.callParent(arguments);
	}
});