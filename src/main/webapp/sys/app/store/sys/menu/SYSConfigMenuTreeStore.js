Ext.define('EJ.store.sys.menu.SYSConfigMenuTreeStore', {
			extend : 'Ext.data.TreeStore',
			model : 'EJ.model.sys.SYSMenuTreeModel',
			proxy : {
				type : 'ajax',
				url : 'menu/findall'
			},
			sorters : [{
						property : 'orderValue',
						direction : 'ASC'
					}]
		});