Ext.define('EJ.store.sys.dictionary.SYSDicStore', {
			extend : 'Ext.data.Store',
			model : 'EJ.model.sys.dictionary.SYSDicModel',
			pageSize : 50,
			proxy : {
				type : 'rest',
				reader : {
					type : 'json',
					root : 'items',
					totalProperty : 'totalCount'
				}
			}

		});