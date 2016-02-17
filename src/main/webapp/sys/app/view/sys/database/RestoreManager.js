Ext.define('EJ.view.sys.database.RestoreManager', {
			extend : 'Ext.panel.Panel',
			alias : 'widget.restoreManager',
			layout : 'fit',
			title : '数据库恢复',
			initComponent : function() {
				this.items = [{
//							xtype : "component",
//							autoEl : {
//								tag : "iframe",
//								src : 'db/back/view',
//								frameborder : 'no',
//								border : '0'
//							}
						}]
				this.callOverridden(arguments);
			}
		});