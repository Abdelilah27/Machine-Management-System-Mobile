$(document).ready(function(){
	
	
// # ===============================
// # = Nombre des produits
// # ===============================
	$.ajax({
		url : 'produits/count',
		data : '',
		type : 'GET',
		success : function(data) {
			$('#produit').html(data);
		},
		error : function(jqXHR, textStatus,
				errorThrown) {
			console.log(textStatus);
		}
	});
	

	
// # ===============================
// # = Nombre des machines achetées par année
// # ===============================
	$.ajax({
		url : 'machines/byYear',
		contentType : "application/json",
		dataType : "json",
		data : '',
		type : 'GET',
		async : false,
		success : function(data) {
			console.log(data);
			var labels = new Array();
			var dt = new Array();
			
			labels.push(data[0][0]);
			dt.push(data[0][1]);
			
			data.forEach ((e, index) =>{
				if(index == 0) 
					return;
			    if(e[0] == labels[labels.length - 1] + 1){  
					labels.push(e[0]);
					dt.push(e[1]);
			    }else {
			    	do{
				    	labels.push(labels[labels.length - 1] + 1);
						dt.push(0);
			    	}while(e[0] != labels[labels.length - 1] + 1);
			    	labels.push(e[0]);
					dt.push(e[1]);
			    }
			});
			
			console.log(labels);
				
			var ctx = document.getElementById('byYear').getContext('2d');
			var myChart = new Chart(ctx, {
			    type: 'line',
			    data: {
			        labels: labels,
			        datasets: [{
			            data: dt,
			            backgroundColor: [
			                'rgba(255, 99, 132, 0.2)',
			                'rgba(54, 162, 235, 0.2)',
			                'rgba(255, 206, 86, 0.2)',
			                'rgba(75, 192, 192, 0.2)',
			                'rgba(153, 102, 255, 0.2)',
			                'rgba(255, 159, 64, 0.2)'
			            ],
			            borderColor: [
			                'rgba(255, 99, 132, 1)',
			                'rgba(54, 162, 235, 1)',
			                'rgba(255, 206, 86, 1)',
			                'rgba(75, 192, 192, 1)',
			                'rgba(153, 102, 255, 1)',
			                'rgba(255, 159, 64, 1)'
			            ],
			            borderWidth: 1
			        }]
			    },
			    options: {
			    	title: {
	                    display: true,
	                    text: 'Evolution des achats',
	                    fontSize: 21,
	                    padding: 20,
	                    fontFamily: 'Calibri',
	                },	
	                legend: {
	                    display: false
	                },
			    	type: 'line',
			        scales: {
			           
	                yAxes: [{
		                	ticks: {
				                    beginAtZero: true
				            },	
		                    scaleLabel: {
		                      display: true,
		                      labelString: 'Nombre des machines'
		                    }
	                }],
	                xAxes: [{
		                    scaleLabel: {
		                      display: true,
		                      labelString: 'Année'
		                    }
		            }],
			        }
			    }
			});
		},
		error : function(jqXHR, textStatus,
				errorThrown) {
			console.log(textStatus);
		}
	});    

// # ===============================
// # = Nombre des machines par marque
// # ===============================
	$.ajax({
		url : 'marques/count',
		contentType : "application/json",
		dataType : "json",
		data : '',
		type : 'GET',
		async : false,
		success : function(data) {
			console.log(data);
			
			var labels = new Array();
			var dt = new Array();
			
			Object.keys(data).forEach(key => {
				   labels.push(key); // returns the keys in an object
				   dt.push(data[key]);  // returns the appropriate value
			});
				
			var ctx = document.getElementById('myChart').getContext('2d');
			var myChart = new Chart(ctx, {
			    type: 'bar',
			    data: {
			        labels: labels,
			        datasets: [{
			            data: dt,
			            backgroundColor: [
			                'rgba(255, 99, 132, 0.2)',
			                'rgba(54, 162, 235, 0.2)',
			                'rgba(255, 206, 86, 0.2)',
			                'rgba(75, 192, 192, 0.2)',
			                'rgba(153, 102, 255, 0.2)',
			                'rgba(255, 159, 64, 0.2)'
			            ],
			            borderColor: [
			                'rgba(255, 99, 132, 1)',
			                'rgba(54, 162, 235, 1)',
			                'rgba(255, 206, 86, 1)',
			                'rgba(75, 192, 192, 1)',
			                'rgba(153, 102, 255, 1)',
			                'rgba(255, 159, 64, 1)'
			            ],
			            borderWidth: 1
			        }]
			    },
			    options: {
			    	title: {
	                    display: true,
	                    text: 'Nombre des machines par marque',
	                    fontSize: 21,
	                    padding: 20,
	                    fontFamily: 'Calibri',
	                },	
	                legend: {
	                    display: false
	                },
			    	type: 'line',
			        scales: {
			           
	                yAxes: [{
		                	ticks: {
				                    beginAtZero: true
				            },	
		                    scaleLabel: {
		                      display: true,
		                      labelString: 'Nombre des machines'
		                    }
	                }],
	                xAxes: [{
		                    scaleLabel: {
		                      display: true,
		                      labelString: 'Marques'
		                    }
		            }],
			        }
			    }
			});
		},
		error : function(jqXHR, textStatus,
				errorThrown) {
			console.log(textStatus);
		}
	});    
});
