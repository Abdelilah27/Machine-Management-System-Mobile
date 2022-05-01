$(document)
		.ready(
				function() {

					table = $('#tmachine')
							.DataTable({
										ajax : {
											url : "machines/all",
											dataSrc : ''
										},
										columns : [
												{
													data : "id"
												},
												{
													data : "reference"
												},
												{
													data : "dateAchat"
												},
												{
													data : "prix"
												},
												{
													data : "marque.libelle"
												},
												{
													"render" : function() {
														return '<button type="button" class="btn btn-outline-danger supprimer">Supprimer</button>';
													}
												},
												{
													"render" : function() {
														return '<button type="button" class="btn btn-outline-secondary modifier">Modifier</button>';
													}
												} ]

									});
					
					$.ajax({
						url:'/marques/all',
						type:'GET',
						success : function(data) {
							var option = '';
							data.forEach(e=>{
								option += '<option value ='+e.id+'>'+e.libelle+'</option>';
							});
							
						$('#marque').append(option);
						},
						error : function(jqXHR, textStatus,
								errorThrown) {
							console.log(textStatus);
						}
						
					});

					$('#btn').click(
							function() {
								var reference = $("#reference");
								var dateAchat = $("#dateAchat");
								var prix = $("#prix");
								var marque = $("#marque");
								if ($('#btn').text() == 'Ajouter') {
									var p = {
										reference : reference.val(),
										dateAchat : dateAchat.val(),
										prix : prix.val(),
										marque : {
											id : marque.val()
										}
									};

									$.ajax({
										url : 'machines/save',
										contentType : "application/json",
										dataType : "json",
										data : JSON.stringify(p),
										type : 'POST',
										async : false,
										success : function(data, textStatus,
												jqXHR) {
											table.ajax.reload();
										},
										error : function(jqXHR, textStatus,
												errorThrown) {
											console.log(textStatus);
										}
									});
									$("#main-content").load(
											"./page/machine.html");
								}
							});

					$('#table-content')
							.on(
									'click',
									'.supprimer',
									function() {

										var id = $(this).closest('tr').find(
												'td').eq(0).text();
										var oldLing = $(this).closest('tr')
												.clone();
										var newLigne = '<tr style="position: relative;" class="bg-light" ><th scope="row">'
												+ id
												+ '</th><td colspan="4" style="height: 100%;">';
										newLigne += '<h4 class="d-inline-flex">Voulez vous vraiment supprimer cette marque ? </h4>';
										newLigne += '<button type="button" class="btn btn-outline-primary btn-sm confirmer" style="margin-left: 25px;">Oui</button>';
										newLigne += '<button type="button" class="btn btn-outline-danger btn-sm annuler" style="margin-left: 25px;">Non</button></td></tr>';

										$(this).closest('tr').replaceWith(
												newLigne);
										$('.annuler').click(
												function() {
													$(this).closest('tr')
															.replaceWith(
																	oldLing);
												});
										$('.confirmer')
												.click(
														function(e) {
															e.preventDefault();
															$
																	.ajax({
																		url : 'machines/delete/'
																				+ id,
																		data : {},
																		type : 'DELETE',
																		async : false,
																		success : function(
																				data,
																				textStatus,
																				jqXHR) {
																			if (data
																					.includes("error") == true) {
																				$(
																						"#error")
																						.modal();
																			} else {
																				table.ajax
																						.reload();
																			}
																		},
																		error : function(
																				jqXHR,
																				textStatus,
																				errorThrown) {
																			$(
																					"#error")
																					.modal();
																		}
																	});

														});

									});

					$('#table-content').on(
							'click',
							'.modifier',
							function() {
								var btn = $('#btn');
								var id = $(this).closest('tr').find('td').eq(0)
										.text();
								
								var reference = $(this).closest('tr').find('td').eq(
										1).text();
								var dateAchat = $(this).closest('tr').find('td')
										.eq(2).text();
								var prix = $(this).closest('tr').find('td')
										.eq(3).text();
								var marque = $(this).closest('tr').find('td')
								.eq(4).text();
							
								
								btn.text('Modifier');
								$("#reference").val(reference);
								$("#dateAchat").val(dateAchat);
								$("#prix").val(prix);
								var op = $('#marque option').filter(function () { return $(this).html() == marque; }).val();
								$("#marque").val(op);
								$("#id").val(id);
								
								btn.click(function(e) {
									e.preventDefault();
									var p = {
										id : $("#id").val(),
										reference : $("#reference").val(),
										dateAchat : $("#dateAchat").val(),
										prix : $("#prix").val(),
										marque : {
											id : $("#marque").val()
											
										}
										
									};
									if ($('#btn').text() == 'Modifier') {
										$.ajax({
											url : 'machines/save',
											contentType : "application/json",
											dataType : "json",
											data : JSON.stringify(p),
											type : 'POST',
											async : false,
											success : function(data,
													textStatus, jqXHR) {
												table.ajax.reload();
												$("#reference").val('');
												$("#dateAchat").val('');
												$("#prix").val('');
												$("#marque").val('');
												btn.text('Ajouter');
											},
											error : function(jqXHR, textStatus,
													errorThrown) {
												console.log(textStatus);
											}
										});
										$("#main-content").load(
												"./page/machine.html");
									}
								});
							});

					
				});
