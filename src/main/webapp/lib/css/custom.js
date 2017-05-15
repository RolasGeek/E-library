 $(document).ready(function () {
    $('.forgot-pass').click(function(event) {
      $(".pr-wrap").toggleClass("show-pass-reset");
    }); 
    
    $('.pass-reset-submit').click(function(event) {
      $(".pr-wrap").removeClass("show-pass-reset");
    }); 
});
 
 function adjust_textarea(h) {
	    h.style.height = "20px";
	    h.style.height = (h.scrollHeight)+"px";
	}