Ext.define('EJ.view.sys.resource.EditResWin', {
	extend : 'Ext.window.Window',
	alias : 'widget.editResWin',
	title : '修改资源信息',
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
														fieldLabel : '资源名称',
														name : 'name',
														labelWidth : 90,
														anchor : '100%'
													}, {
														xtype : 'textfield',
														fieldLabel : 'URL',
														name : 'url',
														labelWidth : 90,
														anchor : '100%'
													}, {
														xtype : 'textfield',
														fieldLabel : '最近操作人',
														name : 'endRegUser',
														readOnly : true,
														labelWidth : 90,
														anchor : '100%'
													}, {
														xtype : 'textarea',
														name : 'remark',
														fieldLabel : '备注(字符≦200)',
														height : 60,
														maxLength : 200,
														anchor : '100%'
													}, {
														fieldLabel : '最近操作时间',
														name : 'endUpdateTime',
														xtype : 'datefield',
														format : 'Y-m-d H:s:i',
														// readOnly : true,
														disabled : true,
														labelWidth : 90,
														anchor : '100%'
													}]
										}]
							}],
					buttons : [{
								text : '提交',
								formBind : true,
								action : 'udpate-resource'
							}, {
								text : '取消',
								scope : this,
								handler : this.close
							}]
				}];
		this.callParent(arguments);
	}
});