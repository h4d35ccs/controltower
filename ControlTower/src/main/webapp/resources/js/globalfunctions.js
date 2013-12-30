/* 
 * JavaScript file with Plane Landing System UI Global Functions
 */

/**
 * @name setCurrentAirportGUI
 * @requires Airport Object
 * Shows all the GUI elements of the selected airport.
 */
function setCurrentAirportGUI(airport){
	$('#btn_selected_airport').html(airport.name);
	$('#airport_name_label').html(airport.name+' Tracks');
	$('#arrival_line_title').html(airport.name+" Radar Arrival Line");
	$('#parking_name').html(airport.name+' Parking');
	$('#controlTower_label').html(airport.name+' Control Tower');
	
	//clean radar for new airport
	$('#arrivals').html('');
	
	//Load tracks according to the airport values;
	$('#landingFieldsContainer').html('');
	var i,value;
	for (i=0; i<airport.numberOfLandingFields; i++){
		value = i+1;
		$('#landingFieldsContainer').append('<div id="start_track_'+value+'" class="track_start center"></div><div id="trackContainer_'+value+'" class="trackContainer"><div id="track_'+value+'" class="alert track center"></div><div id="title_track_'+value+'" class="title_track">Track '+value+'</div></div>');
	}
}

/**
 * @name addPlane
 * @requires Aircraft Object
 * Creates a new plane in the UI, updates the counter values
 */
function addPlane(aircraft){

   $("#arrivals").append('<li class="plane" id="plane_'+aircraft.id+'">'+
                            '<img class="img-rounded" alt="plane'+aircraft.id+'" src="resources/img/plane.png"><br/>'+
                            '<strong>'+aircraft.id+'</strong>'+
                        '</li>');     
   
   //listener to land itself
   $('#plane_'+aircraft.id).bind('click',function(){
	   var land = false;
       if($("ul#arrivals").children().hasClass('plane')){
          $('[id^="track_"]').each(function(){
              if($(this).is(':empty')){
           	   land = true;
           	   ControlTower.land(aircraft.id); //return the plane which can land
                  return false;
              }
          });
          
          if(!land){
              alert('All the Landing Fields are busy.\nPlease wait...'); 
          }
       }
       else{
       	alert("There are no planes in the Radar Arrival Line.");
       }
	   
   });
   
}

/**
 * @name removePlane
 * @requires Aircraft Id
 * Deletes the last plane on the UI arrival line, updates the counter values
 */
function removePlane(aircraftId){
     $("ul#arrivals li#plane_"+aircraftId).remove();
}

/**
 * @name landPlane
 * @requires Aircraft Object
 * Chooses a landing field for each plane
 */
function landPlane(aircraft){
      var land = false;
      if($("ul#arrivals").children().hasClass('plane')){
         $('[id^="track_"]').each(function(){
             if($(this).is(':empty')){
                 $(this).append('<ul id="landing_'+$(this).attr('id')+'" class="nav nav-pills landing"></ul>');
                 $("#landing_"+$(this).attr('id')).append($("li#plane_"+aircraft.id));
                 $(this).addClass('track_selected');
                 $("#start_"+$(this).attr('id')).addClass('start_track_selected');
                 //remove listeners to the plane
                 $("li#plane_"+aircraft.id).unbind('click');
                 land = true;
                 return false;
             }
         });
         
         if(!land){
             alert('All the Landing Fields are busy. Please wait...'); 
         }
      }
}

/**
 * @name parkPlane
 * @requires Aircraft Object
 * Removes the planes from the landing fields
 */
function parkPlane(aircraft){
   var parking_plane = 'plane_'+aircraft.id; //get the values from controller
   var free_track = $('#'+parking_plane).parent().parent().attr("id"); //get the values from controller

   //move the plane to the parking spot
   $('#parking_spots').append($("li#"+parking_plane));
   
   //unbind and add the new listeners
   $("li#"+parking_plane).unbind('click');
   
   //add the listeners to be ready for take off
   $("li#"+parking_plane).bind('click',function(){
	   var takeoff = false; 
	   	if($("ul#parking_spots").children().hasClass('plane')){
	   		 $('[id^="track_"]').each(function(){
	   			 if($(this).is(':empty')){
	   				takeoff = true;
	   				//get the id of the next plane
	   				parking_plane = parking_plane.replace("plane_","");
	   			    ControlTower.prepareForTakeOff(parking_plane);
	                  return false;
	                }
	   		 });
	   		 
	   		 if(!takeoff){
	                alert('All the Landing Fields are busy.\nPlease wait...'); 
	            }
	   	 }
	   	 else{
	        	alert("There are no planes in the parking spots.");
	        }
	   
   });
   
   //removing the plane from the track
   $('#landing_'+free_track).remove();
   $('#'+free_track).removeClass('track_selected');
   $('#start_'+free_track).removeClass('start_track_selected');
   
}

/**
 * @name createNewAirport
 * @requires airport name, tracks number
 * Create the new airport according to the parameters
 */
function createNewAirport(airport_name,tracks_number){
	Airport.create(airport_name, tracks_number);
	//reload the list
	$('#airport_list_select .dropdown-menu').html('');

	airportList = Airport.list();

    // filling select list of airports
    for(a in airportList){
		  var airport = airportList[a];
	      $('#airport_list_select .dropdown-menu')
	      .append('<li class="airport-select-item" data-id="'+airport.name+'">'+
	    		  '<a href="#">'+
	    		  airport.name+
	    		  '</a></li>');
	}
	//show list    
    $('#airport_list_select').show();

    //add listeners to display the selected airports
    displayAirport();
}

/**
 * @name displayAirport
 * Displays the selected airport
 */
function displayAirport(){
	//set the current airport on the GUI
    $('.airport-select-item').on('click',function(){
    	$('#airport_container_loader').hide();
    	$('#airport_container').show();
        airport = Airport.setCurrent($(this).attr('data-id'));
        setCurrentAirportGUI(airport);
     });
}

/**
 * @name readyForTakeOff
 * Moves the airplanes to the track for taking off
 */
function readyForTakeOff(aircraft){
	var takeoff_plane = aircraft.id;
	var land = false;
    if($("ul#parking_spots").children().hasClass('plane')){
       $('[id^="track_"]').each(function(){
           if($(this).is(':empty')){
               $(this).append('<ul id="takeoff_'+$(this).attr('id')+'" class="nav nav-pills takeoff"></ul>');
               $("#takeoff_"+$(this).attr('id')).append($("li#plane_"+takeoff_plane));
               $(this).addClass('track_selected');
               $("#start_"+$(this).attr('id')).addClass('start_track_selected');
               //remove listeners to the plane
               $("li#plane_"+takeoff_plane).unbind('click');
               land = true;
               return false;
           }
       });
       
       if(!land){
           alert('All the Landing Fields are busy. Please wait...'); 
       }
    }
}

/**
 * @name takeOff
 * The plane moves on the track and departure from the airport
 */
function takeOff(aircraft){
	//TODO change the variable to the actual value
//	var takeoff_plane =  $('[id^="takeoff_track_"]').children().first().attr('id').replace("plane_","");
	var takeoff_plane = aircraft.id;
	var width = $('li#plane_'+takeoff_plane).parent().width();
	var move = width/1.5;
	var free_track = $('#plane_'+takeoff_plane).parent().parent().attr("id"); //get the values from controller
	//animation to move the plane
	$('li#plane_'+takeoff_plane).animate(
			{
				marginLeft: "+="+move,
			},
			4000,
			"linear",
			function(){
				//remove plane from track
			   $('#takeoff_'+free_track).remove();
			   $('#'+free_track).removeClass('track_selected');
			   $('#start_'+free_track).removeClass('start_track_selected');
			}
	);
}

/**
 * @name singlePlaneReadyForTakeOff
 * Moves the airplanes to the track for taking off
 */
function singlePlaneReadyForTakeOff(id){
	//TODO use the values 
	var airplane_id = id.replace("plane_","");
	var land = false;
	if($("ul#parking_spots").children().hasClass('plane')){
	       $('[id^="track_"]').each(function(){
	           if($(this).is(':empty')){
	               $(this).append('<ul id="takeoff_'+$(this).attr('id')+'" class="nav nav-pills takeoff"></ul>');
	               $("#takeoff_"+$(this).attr('id')).append($("li#plane_"+airplane_id));
	               $(this).addClass('track_selected');
	               $("#start_"+$(this).attr('id')).addClass('start_track_selected');
	               //remove listeners to the plane
	               $("li#plane_"+airplane_id).unbind('click');
	               land = true;
	               return false;
	           }
	       });
	       
	       if(!land){
	           alert('All the Landing Fields are busy. Please wait...'); 
	       }
	    }
}