Ext.define('EJ.view.Viewport', {
			extend : 'Ext.container.Viewport',
			requires : [
			'Ext.tab.Panel',
			'Ext.layout.container.Border', 
			'Ext.toolbar.TextItem',
			'Ext.chart.*',
			'Ext.form.*',
			'Ext.grid.column.RowNumberer'
			],
			layout : {
				type : 'fit'
			},

			items : [{
						xtype : 'sys-main'
					}]
		});
