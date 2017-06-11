var app = angular.module("myApp")

app.controller("insertBookController", ['$scope', '$rootScope', '$cookieStore', 'BookService' , '$location', function($scope, $rootScope, $cookieStore, BookService ,$location) {
	 $scope.preview = false;
	 $scope.genres = ['Fictional','Comedy','Drama','Horror','Non-fiction','Realistic fiction','Romance novel','Satire','Tragedy','Tragicomedy','Fantasy','Mythology'];
	 $scope.bookgenres = [{"bookId" : null, "genre" : null}];
	 
	 
	 $scope.$watch('book.file', function(newfile, oldfile) {
	      if(angular.equals(newfile, oldfile) ){
	        return;
	      }
	      if(newfile.type != 'image/png' && newfile.type != 'image/jpg') {
	    	  $scope.preview = false;
	    	  $scope.error = true;
	    	  return;
	      } else {
	    	  $scope.error = false;
	    	  $scope.preview = true;
	      }
	 
	  });
	$scope.addLine = function() {
		 $scope.bookgenres.push({"bookId" : null, "genre" : null});
		 console.log($scope.bookgenres);
	};
	
	$scope.insert = function(book) {
		var formdata = new FormData();
		formdata.append('image', book.file);
		formdata.append('file1', book.file1);
		console.log(book);
		var json_test = JSON.stringify(book.data);
		var genres  = angular.toJson($scope.bookgenres);
		console.log(genres);
		formdata.append('book', json_test);
		formdata.append('genres', genres);
		
		console.log(formdata);
		BookService.insert(formdata).success(function(data) {
			$scope.render=true;
			$scope.alertclass="alert alert-success";
			$scope.alertmessage="Book has been inserted";
		})
	}
		
	    
}]);