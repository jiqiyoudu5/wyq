Ext.define('EJ.store.sys.dictionary.SYSDicTreeStore', {
			extend : 'Ext.data.TreeStore',
			model : 'EJ.model.sys.dictionary.SYSDicTreeModel',
			proxy : {
				type : 'rest',
				url : 'dictionary/tree/ajax'
			},
			root : {
				id : 'root'
			},
			folderSort : true,
			sorters : [{
						property : 'orderValue',
						direction : 'ASC'
					}]
		});