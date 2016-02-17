Ext.define('EJ.model.sys.user.UserModel', {
			extend : 'Ext.data.Model',
			fields : ['username', 'password', {
						name : 'enabled',
						type : 'boolean'
					}, {
						name : 'locked',
						type : 'boolean'
					}, 'businessName', 'phone', 'tel', 'email', 'address',
					'district', {
						name : 'regTime',
						convert : timeConvertor
					}, 'vip', {
						name : 'vipendtime',
						convert : timeConvertor
					}, 'remark', 'updateUser', {
						name : 'updateTime',
						convert : timeConvertor
					}],
			idProperty : 'username',
			proxy : {
				type : 'rest',
				actionMethods : {
					create : 'POST',
					read : 'GET',
					update : 'PUT',
					destroy : 'DELETE'
				},
				api : {
					create : 'user/add',
					read : 'user/read',
					update : 'user/update',
					destroy : 'user/delete'
				}
			}
		});