var pageNumber = 0;
	var totalPageNumber = 0;
	// 頁面載入完成後要執行的程序
	$(document).ready(function() {
		// 驗證註冊
		$('#myForm').validate({
			onsubmit: false,
			onkeyup: false,
			rules: {
				fname: { // fname 指的是 input 輸入框標籤的 name 而不是 id
					required: true,
					rangelength: [2, 50]
				}
			},
			messages: { // 自訂錯誤訊息
				fname: { 
					required: "請輸入基金名稱",
					rangelength: "基金名稱長度必須介於{0}~{1}之間"
				}
			}
		});
		// Fund List 的資料列表
		table_list();
		// 註冊相關事件
		$('#add').on('click', function() {
			addOrUpdate('POST');
		});
		$('#myTable').on('click', 'tr', function() {
			getItem(this);
		});
		$('#upt').on('click', function() {
			addOrUpdate('PUT');
		});
		$('#del').on('click', function() {
			deleteItem();
		});
		$('#rst').on('click', function() {
			btnAttr(0);
			$('#myForm').trigger('reset');
		});
	});
	
	// 分頁查詢
	function queryPage(pageNumber) {
		var path = "../mvc/lab/fund/";
		if(pageNumber > 0) {
			path = "../mvc/lab/fund/page/" + pageNumber;
		}
		this.pageNumber = pageNumber;
		// 取得所有 fund 資料
		$.get(path, function(datas, status) {
			console.log(datas);
			console.log(status);
			// 清除目前 myTable 上的舊有資料
			$('#myTable tbody > tr').remove();
			// 將資料 datas 依序放入 myTable 中
			$.each(datas, function(i, item){
				var html = '<tr><td>{0}</td><td>{1}</td><td>{2}</td><td>{3}</td></tr>';
				$('#myTable').append(String.format(html, item.fid, item.fname, item.createtime, parseFundstocks(item.fundstocks)));
			});
		});
	}
	
	function parseFundstocks(datas) {
		if(datas == null) return "";
		var str = '';
		$.each(datas, function(i, item){
			str += item.symbol + '、';
			return str;
		});
		str = str.substring(0, str.length-1);
		return str;
	}
	
	function getItem(elem) {
		var fid = $(elem).find('td').eq(0).text().trim();
		console.log(fid);
		var path = '../mvc/lab/fund/' + fid;
		var func = function(fund) {
			console.log(fund);
			// 將資料配置到 myForm 表單中
			$('#myForm').find('#fid').val(fund.fid);
			$('#myForm').find('#fname').val(fund.fname);
			// 修改 btn 狀態
			btnAttr(1);
			// 該筆資料是否能刪除，取決於 fund 物件下面是否有 fundstock 陣列物件
			console.log(fund.fundstocks.length);
			if(fund.fundstocks.length > 0) {
				$('#myForm').find('#del').attr('disabled', true);
			}
		};
		$.get(path, func);
	}
	
	function addOrUpdate(method) {
		// 驗證
		console.log($('#myForm').valid());
		if(!$('#myForm').valid()) { // 判斷是否表單驗證成功？
			return;
		}
		// 將表單欄位資料json物件序列化
		var jsonObject = $('#myForm').serializeObject();
		// 將json物件轉為json字串
		var jsonString = JSON.stringify(jsonObject);
		console.log(jsonString);
		// 將資料傳遞到後端
		$.ajax({
			url: "../mvc/lab/fund/",
			type: method,
			contentType: 'application/json;charset=utf-8',
			data: jsonString,
			success: function(respData) {
				console.log(respData);
				// 列表資料更新
				table_list();
				// rst 狀態
				$('#myForm').find('#rst').trigger('click');
			}
		});
	}
	
	function deleteItem() {
		var fid = $('#myForm').find('#fid').val();
		$.ajax({
			url: '../mvc/lab/fund/' + fid,
			type: 'DELETE',
			contentTyep: 'application/json;charset=utf-8',
			success: function(respData) {
				console.log(respData);
				// 列表資料更新
				table_list();
				// rst 狀態
				$('#myForm').find('#rst').trigger('click');
			},
			error: function(http, textStatus, errorThrown) {
				console.log("http:" + http);
				console.log("textStatus:" + textStatus);
				console.log("errorThrown:" + errorThrown);
				var errorInfoText = JSON.stringify(http);
				console.log(errorInfoText.includes('REFERENCES'));
				if(errorInfoText.includes('REFERENCES')) {
					alert('該筆資料無法刪除，原因：因為此基金下有成分股的參照');
				} else {
					alert('該筆資料無法刪除，原因：' + textStatus);
				}
			}
		});
	}
	
	// Fund List 的資料列表
	function table_list() {
		queryPage(pageNumber);
		// 分頁數
		page_list();
	}
	
	// 分頁數
	function page_list() {
		var path = "../mvc/lab/fund/totalPagecount";
		$('#myPageSpan').empty();
		$.get(path, function(totalPageNumber) {
			this.totalPageNumber = totalPageNumber;
			for(var i=1;i<=totalPageNumber;i++) {
				var html = '<span class="mylink" onclick="queryPage({0});">{1}</span>&nbsp;';
				$('#myPageSpan').append(String.format(html, i, i));
			}
		});
	}
	
	function btnAttr(status) {
		$('#myForm').find('#add').attr('disabled', status != 0);
		$('#myForm').find('#upt').attr('disabled', status == 0);
		$('#myForm').find('#del').attr('disabled', status == 0);
	}