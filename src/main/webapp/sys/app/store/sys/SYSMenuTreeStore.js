Ext.define('EJ.store.sys.SYSMenuTreeStore', {
			extend : 'Ext.data.TreeStore',
			model : 'EJ.model.sys.SYSMenuTreeModel',
			proxy : {
				type : 'ajax',
				url : 'menu/byuserline',
				autoLoad : true
			},
			sorters : [{
						property : 'orderValue',
						direction : 'ASC'
					}]
		});