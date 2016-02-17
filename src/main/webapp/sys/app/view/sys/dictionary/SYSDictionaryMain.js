Ext.define('EJ.view.sys.dictionary.SYSDictionaryMain', {
			extend : 'Ext.panel.Panel',
			alias : 'widget.sysDictionaryMain',
			renderTo : Ext.getBody(),
			layout : 'border',
			bodyPadding : 2,
			items : [{
						title : '字典库树',
						region : 'west',
						xtype : 'sysDicTreeList',
						width : 350,
						collapsible : true,
						autoScroll : true,
						split : true
					}, {
						xtype : 'sysDicList',
						title : '字典子库管理',
						region : 'center',
						border : false
					}]

		});