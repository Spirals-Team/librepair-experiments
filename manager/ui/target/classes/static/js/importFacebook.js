var postsToImport = [];
var checkBoxSelector = "input[type='checkbox']";
$(document).ready(function(){
	$(checkBoxSelector).each(function(){
		if(!isImportAllCheckBox(this.id)){
			$(this).change(function(){
				var fullId = this.id;
				var facebookId = fullId.split('-')[1];
				toggleImport(facebookId);
			});
		}
		if(isImportAllCheckBox(this.id)){
			$(this).change(function(){
				toggleAllImport();
			});
		}
	});
})


function toggleAllImport(){
	$(checkBoxSelector).each(function(){
		if(!isImportAllCheckBox(this.id)){
			var fullId = this.id;
			var facebookId = fullId.split('-')[1];
			if(isAllImportChecked()){
				if(!computeIsToImport(facebookId)){
					$(this).prop("checked", true);
					toggleImport(facebookId);
				}
			}else{
				if(computeIsToImport(facebookId)){
					$(this).prop("checked", false);
					toggleImport(facebookId);
				}
			}
		}
	});
}

function toggleImport(facebookId){
	
	var isToImport = computeIsToImport(facebookId);
	if(isToImport){
		var post = computePostToImport(facebookId);
		if(!isAlreadyInImport(facebookId)){
			postsToImport.push(post);
		}
	}else{
		postsToImport.forEach(function(post){
			if(post.facebookId === facebookId){
				var index = postsToImport.indexOf(post);
				if(index !== -1){
					postsToImport.splice(index, 1);
				}
			}
		});
	}
}

function isAlreadyInImport(facebookId){
	var isAlreadyInImport = false;
	postsToImport.forEach(function(post){
		if(post.facebookId === facebookId){
			isAlreadyInImport = true;
		}
	});
	return isAlreadyInImport;
}

function isImportAllCheckBox(checkboxId){
	return checkboxId === 'importAll';
}

function isAllImportChecked(){
	var checkboxId = '#importAll';
	var checkboxInput = $(checkboxId);
	return checkboxInput.is(":checked");
}

function computeIsToImport(facebookId){
	var checkboxId = '#checkbox-' + facebookId;
	var checkboxInput = $(checkboxId);
	return checkboxInput.is(":checked");
}

function computePostToImport(facebookId){
	var postToImport = {};
	
	var inputAuthor = $("#feed_author_" + facebookId);
	var inputTitle = $("#feed_title_"+ facebookId);
	var inputType = $("#feed_type_"+ facebookId);
	var inputDescription = $("#feed_description_"+ facebookId);
	var inputPhotoUrl = $("#feed_photoUr_"+ facebookId);
	var inputVideoUrl = $("#feed_videoUrl_"+ facebookId);
	var inputLinkUrl = $("#feed_linkUrl_"+ facebookId);
	var inputCreationDate = $("#feed_creationDate_"+ facebookId);
	var inputObjectId = $("#feed_objectId_" + facebookId);
	
	postToImport.facebookId = facebookId;
	postToImport.author = inputAuthor.val();
	postToImport.title = inputTitle.val();
	postToImport.type = inputType.val();
	postToImport.description = inputDescription.val();
	postToImport.photoUrl = inputPhotoUrl.val();
	postToImport.videoUrl = inputVideoUrl.val();
	postToImport.linkUrl = inputLinkUrl.val();
	postToImport.creationDate = inputCreationDate.val();
	postToImport.objectId = inputObjectId.val();
	
	return postToImport;
}

function postImportFacebook(){
	
	$("#facebookImportForm").hide();
	$(".loader").show();
	$(".card-loader").show();
	var url = "/manager/facebook/import";
	var urlFallback = "/manager/news";
	var request = {};
	request.postsToImport = postsToImport;
	var data = JSON.stringify(request);
	$.ajax({
        type: "POST",
        url: url,
        data: data,
        contentType: "application/json; charset=utf-8",
        crossDomain: true,
        dataType: "json",
        success: function () {
        	$("#facebookImportForm").show();
    		$(".loader").hide();
    		$(".card-loader").hide();
    		window.location.href= urlFallback;
        },
        error: function(){
        	window.location.href= urlFallback;
        }
     });
}