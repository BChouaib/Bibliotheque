$(document).ready(function(){
    
    $("#authForm").submit(function () {
	var nomUtilisateur=$("#nomUtilisateur").val();
	var motpasse=$("#motpasse").val();
	var rep=0;
    	$.ajax({  
            type: "GET",  
            url: "ControleBiblio",  
            data: "appelerPour=authentifier&nomUtilisateur="+nomUtilisateur+"&motpasse="+motpasse , 
            async:false,
            success: function(msg){  
            	if(msg.localeCompare("0")!=0) {
            		rep=1;
               	}            
            }
        });  
    	if(rep==0){
    		$(".modal-header").css("background-color", "red");
    		$("#motpasse").val("");
    		setTimeout(function(){
    					  $(".modal-header").css("background-color", "#5cb85c")
    				  }, 3000);
    		return false;
    	}
    	
        
    });
	
});