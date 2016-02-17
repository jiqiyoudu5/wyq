Ext.define('EJ.view.sys.roles.SYSRoleMain', {
			extend : 'Ext.panel.Panel',
			alias : 'widget.sysRoleMain',
			renderTo : Ext.getBody(),
			layout : 'border',
			bodyPadding : 2,
			items : [{
						title : '角色库',
						region : 'west',
						xtype : 'sysRoleList',
						width : 380,
						collapsible : true,
						autoScroll : true,
						split : true
					}, {
						// Tab窗口
						xtype : 'tabpanel',
						id : 'roleTabs',
						region : 'center',
						border : false,
						autoScroll : true,
						layout : 'fit',
						items : [{
									xtype : 'sysRoleResourcesTab'
								}, {
									xtype : 'sysRoleUsersTab'
								}, {
									xtype : 'sysRoleMenuTab'
								}]
					}]
		});