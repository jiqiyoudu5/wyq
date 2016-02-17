Ext.define('EJ.store.sys.user.UserStore', {
			extend : 'Ext.data.Store',
			model : 'EJ.model.sys.user.UserModel',
			pageSize : 50,
			proxy : {
				type : 'ajax',
				url : 'user/findall',
				reader : {
					type : 'json',
					root : 'items',
					totalProperty : 'totalCount'
				}
			}
		});