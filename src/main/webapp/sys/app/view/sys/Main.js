Ext.define('EJ.view.sys.Main', {
			extend : 'Ext.container.Container',
			xtype : 'sys-main',
			layout : {
				type : 'border'
			},
			bodyPadding : 2,
			items : [{
						xtype : 'sysBanner',
						// margins : '5 0 0 5',
						region : "north",
						split : true
					}, {
						title : '系统菜单栏',
						region : 'west',
						xtype : 'menuTree',
						width : '15%',
						id : 'west-region-container',
						split : true
					}, {
						// Tab窗口
						id : 'TAB_ID',
						xtype : 'tabpanel',
						region : 'center',
						border : false,
						items : [{
									xtype : 'sysHome'
								}]
					}]

		});