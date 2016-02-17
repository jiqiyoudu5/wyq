Ext.define('EJ.view.sys.database.BackManager', {
			extend : 'Ext.panel.Panel',
			alias : 'widget.backManager',
			layout : 'fit',
			title : '数据库备份',
			initComponent : function() {
				this.items = [{
							xtype : "component",
							autoEl : {
								tag : "iframe",
								src : 'db/back/view',
								frameborder : 'no',
								border : '0'
							}
						}]
				this.callOverridden(arguments);
			}
		});