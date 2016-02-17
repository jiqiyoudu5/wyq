Ext.define('EJ.view.sys.SYSHome', {
			title : '首  页',
			alias : 'widget.sysHome',
			extend : 'Ext.panel.Panel',
			layout : 'border',
			items : [{
						title : '统计与分析',
						height : '50%',
						bodyPadding : '10 5 0',
						collapsible : true,
						region : 'north',
						xtype : 'panel',
						layout : 'hbox',
						items : [{
									width : '80%',
									height : '100%',
									xtype : 'userChart'
								}, {
									bodyPadding : '15 20 0',
									xtype : 'findChartByTimeForm'
								}]
					}, {
						xtype : 'tabpanel',
						region : 'center',
						border : false,
						items : [{
									xtype : 'expireVipUserList'
								}, {
									xtype : 'endVipUserList'
								}]
					}]
		});