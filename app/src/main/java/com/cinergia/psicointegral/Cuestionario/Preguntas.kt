package com.cinergia.psicointegral.Cuestionario

data class Pregunta(val pregunta: String, val tipo: String)

data class Seccion(val seccion: Map<String, Pregunta>)

data class Cuestionario(val cuestionario: Map<String, List<Seccion>>)

fun obtenerCuestionarios(): Cuestionario {
    return Cuestionario(
        mapOf(
            "cuestionario_01" to listOf(
                Seccion(
                    mapOf(
                        "01" to Pregunta("¿Ha presenciado o sufrido alguna vez, durante o con motivo del trabajo un acontecimiento como los siguientes: Accidente que tenga como consecuencia la muerte, la pérdida de un miembro o una lesión grave?", "si_no"),
                        "02" to Pregunta("¿Ha presenciado o sufrido alguna vez, durante o con motivo del trabajo, asaltos?", "si_no"),
                        "03" to Pregunta("¿Ha presenciado o sufrido alguna vez, durante o con motivo del trabajo, actos violentos que derivaron en lesiones graves?", "si_no"),
                        "04" to Pregunta("¿Ha presenciado o sufrido alguna vez, durante o con motivo del trabajo, secuestro?", "si_no"),
                        "05" to Pregunta("¿Ha presenciado o sufrido alguna vez, durante o con motivo del trabajo, amenazas?", "si_no"),
                        "06" to Pregunta("¿Ha presenciado o sufrido alguna vez, durante o con motivo del trabajo, cualquier otro que ponga en riesgo su vida o salud, y/o la de otras personas?", "si_no")
                    )
                ),
                Seccion(
                    mapOf(
                        "01" to Pregunta("¿Ha tenido recuerdos recurrentes sobre el acontecimiento que le provocan malestares?", "si_no"),
                        "02" to Pregunta("¿Ha tenido sueños de carácter recurrente sobre el acontecimiento, que le producen malestar?", "si_no")
                    )
                ),
                Seccion(
                    mapOf(
                        "01" to Pregunta("¿Se ha esforzado por evitar todo tipo de sentimientos, conversaciones o situaciones que le puedan recordar el acontecimiento?", "si_no"),
                        "02" to Pregunta("¿Se ha esforzado por evitar todo tipo de actividades, lugares o personas que motivan recuerdos del acontecimiento?", "si_no"),
                        "03" to Pregunta("¿Ha tenido dificultad para recordar alguna parte importante del evento?", "si_no"),
                        "04" to Pregunta("¿Ha disminuido su interés en sus actividades cotidianas?", "si_no"),
                        "05" to Pregunta("¿Se ha sentido usted alejado o distante de los demás?", "si_no"),
                        "06" to Pregunta("¿Ha notado que tiene dificultad para expresar sus sentimientos?", "si_no"),
                        "07" to Pregunta("¿Ha tenido la impresión de que su vida se va a acortar, que va a morir antes que otras personas o que tiene un futuro limitado?", "si_no")
                    )
                ),
                Seccion(
                    mapOf(
                        "01" to Pregunta("¿Ha tenido usted dificultades para dormir?", "si_no"),
                        "02" to Pregunta("¿Ha estado particularmente irritable o le han dado arranques de coraje?", "si_no"),
                        "03" to Pregunta("¿Ha tenido dificultad para concentrarse?", "si_no"),
                        "04" to Pregunta("¿Ha estado nervioso o constantemente en alerta?", "si_no"),
                        "05" to Pregunta("¿Se ha sobresaltado fácilmente por cualquier cosa?", "si_no")
                    )
                )
            ),
            "cuestionario_02" to listOf(
                Seccion(
                    mapOf(
                        "01" to Pregunta("Mi trabajo me exige hacer mucho esfuerzo físico", "frecuencia"),
                        "02" to Pregunta("Me preocupa sufrir un accidente en mi trabajo", "frecuencia"),
                        "03" to Pregunta("Considero que las actividades que realizo son peligrosas", "frecuencia"),
                        "04" to Pregunta("Por la cantidad de trabajo que tengo debo darme tiempo adicional a mi turno", "frecuencia"),
                        "05" to Pregunta("Por la cantidad de trabajo que tengo debo trabajar sin parar", "frecuencia"),
                        "06" to Pregunta("Considero que es necesario mantener un ritmo de trabajo acelerado", "frecuencia"),
                        "07" to Pregunta("Mi trabajo exige que esté muy concentrado", "frecuencia"),
                        "08" to Pregunta("Mi trabajo requiere que memorice mucha información", "frecuencia"),
                        "09" to Pregunta("Mi trabajo exige que atienda varios asuntos al mismo tiempo", "frecuencia"),
                        "10" to Pregunta("En mi trabajo soy responsable de cosas de mucho valor", "frecuencia"),
                        "11" to Pregunta("Respondo ante mi jefe por los resultados de toda mi área de trabajo", "frecuencia"),
                        "12" to Pregunta("En mi trabajo me dan órdenes contradictorias", "frecuencia"),
                        "13" to Pregunta("Considero que en mi trabajo me piden hacer cosas innecesarias", "frecuencia"),
                        "14" to Pregunta("Trabajo horas extras más de tres veces a la semana", "frecuencia"),
                        "15" to Pregunta("Mi trabajo me exige laborar en días de descanso, festivos o fines de semana", "frecuencia"),
                        "16" to Pregunta("Considero que el tiempo en el trabajo es mucho y perjudica mis actividades familiares o personales", "frecuencia"),
                        "17" to Pregunta("Pienso en las actividades familiares o personales cuando estoy en mi trabajo", "frecuencia"),
                        "18" to Pregunta("Mi trabajo permite que desarrolle nuevas habilidades", "frecuencia"),
                        "19" to Pregunta("En mi trabajo puedo aspirar a un mejor puesto", "frecuencia"),
                        "20" to Pregunta("Durante mi jornada de trabajo puedo tomar pausas cuando las necesito", "frecuencia"),
                        "21" to Pregunta("Puedo decidir la velocidad a la que realizo mis actividades en mi trabajo", "frecuencia"),
                        "22" to Pregunta("Puedo cambiar el orden de las actividades que realizo en mi trabajo", "frecuencia"),
                        "23" to Pregunta("Me informan con claridad cuáles son mis funciones", "frecuencia"),
                        "24" to Pregunta("Me explican claramente los resultados que debo obtener en mi trabajo", "frecuencia"),
                        "25" to Pregunta("Me informan con quién puedo resolver problemas o asuntos de trabajo", "frecuencia"),
                        "26" to Pregunta("Me permiten asistir a capacitaciones relacionadas con mi trabajo", "frecuencia"),
                        "27" to Pregunta("Recibo capacitación útil para hacer mi trabajo", "frecuencia"),
                        "28" to Pregunta("Mi jefe tiene en cuenta mis puntos de vista y opiniones", "frecuencia"),
                        "29" to Pregunta("Mi jefe ayuda a solucionar los problemas que se presentan en el trabajo", "frecuencia"),
                        "30" to Pregunta("Puedo confiar en mis compañeros de trabajo", "frecuencia"),
                        "31" to Pregunta("Cuando tenemos que realizar trabajo de equipo los compañeros colaboran", "frecuencia"),
                        "32" to Pregunta("Mis compañeros de trabajo me ayudan cuando tengo dificultades", "frecuencia"),
                        "33" to Pregunta("En mi trabajo puedo expresarme libremente sin interrupciones", "frecuencia"),
                        "34" to Pregunta("Recibo críticas constantes a mi persona y/o trabajo", "frecuencia"),
                        "35" to Pregunta("Recibo burlas, calumnias, difamaciones, humillaciones o ridiculizaciones", "frecuencia"),
                        "36" to Pregunta("Se ignora mi presencia o se me excluye de las reuniones de trabajo y en la toma de decisiones", "frecuencia"),
                        "37" to Pregunta("Se manipulan las situaciones de trabajo para hacerme parecer un mal trabajador", "frecuencia"),
                        "38" to Pregunta("Se ignoran mis éxitos laborales y se atribuyen a otros trabajadores", "frecuencia"),
                        "39" to Pregunta("Me bloquean o impiden las oportunidades que tengo para obtener ascenso o mejora en mi trabajo", "frecuencia"),
                        "40" to Pregunta("He presenciado actos de violencia en mi centro de trabajo", "frecuencia")
                    )
                ),
                Seccion(
                    mapOf(
                        "01" to Pregunta("En mi trabajo debo brindar servicio a clientes o usuarios", "si_no")
                    )
                ),
                Seccion(
                    mapOf(
                        "41" to Pregunta("Atiendo clientes o usuarios muy enojados", "frecuencia"),
                        "42" to Pregunta("Mi trabajo me exige atender personas muy necesitadas de ayuda o enfermas", "frecuencia"),
                        "43" to Pregunta("Para hacer mi trabajo debo demostrar sentimientos distintos a los míos", "frecuencia")
                    )
                ),
                Seccion(
                    mapOf(
                        "01" to Pregunta("Soy jefe de otros trabajadores", "si_no")
                    )
                ),
                Seccion(
                    mapOf(
                        "44" to Pregunta("Comunican tarde los asuntos de trabajo","frecuencia"),
                        "45" to Pregunta("Dificultan el logro de los resultados del trabajo","frecuencia"),
                        "46" to Pregunta("Ignoran las sugerencias para mejorar su trabajo", "frecuencia")

                    )
                )
            ),
            "cuestionario_03" to listOf(
                Seccion(
                    mapOf(
                        "01" to Pregunta("El espacio donde trabajo me permite realizar mis actividades de manera segura e higiénica", "frecuencia"),
                        "02" to Pregunta("Mi trabajo me exige hacer mucho esfuerzo físico", "frecuencia"),
                        "03" to Pregunta("Me preocupa sufrir un accidente en mi trabajo", "frecuencia"),
                        "04" to Pregunta("Considero que en mi trabajo se aplican las normas de seguridad y salud en el trabajo", "frecuencia"),
                        "05" to Pregunta("Considero que las actividades que realizo son peligrosas", "frecuencia"),
                        "06" to Pregunta("Por la cantidad de trabajo que tengo debo quedarme tiempo adicional a mi turno", "frecuencia"),
                        "07" to Pregunta("Por la cantidad de trabajo que tengo debo trabajar sin parar", "frecuencia"),
                        "08" to Pregunta("Considero que es necesario mantener un ritmo de trabajo acelerado", "frecuencia"),
                        "09" to Pregunta("Mi trabajo exige que esté muy concentrado", "frecuencia"),
                        "10" to Pregunta("Mi trabajo requiere que memorice mucha información", "frecuencia"),
                        "11" to Pregunta("En mi trabajo tengo que tomar decisiones difíciles muy rápido", "frecuencia"),
                        "12" to Pregunta("Mi trabajo exige que atienda varios asuntos al mismo tiempo", "frecuencia"),
                        "13" to Pregunta("En mi trabajo soy responsable de cosas de mucho valor", "frecuencia"),
                        "14" to Pregunta("Respondo ante mi jefe por los resultados de toda mi área de trabajo", "frecuencia"),
                        "15" to Pregunta("En el trabajo me dan órdenes contradictorias", "frecuencia"),
                        "16" to Pregunta("Considero que en mi trabajo me piden hacer cosas innecesarias", "frecuencia"),
                        "17" to Pregunta("Trabajo horas extras más de tres veces a la semana", "frecuencia"),
                        "18" to Pregunta("Mi trabajo me exige laborar en días de descanso, festivos o fines de semana", "frecuencia"),
                        "19" to Pregunta("Considero que el tiempo en el trabajo es mucho y perjudica mis actividades familiares o personales", "frecuencia"),
                        "20" to Pregunta("Debo atender asuntos de trabajo cuando estoy en casa", "frecuencia"),
                        "21" to Pregunta("Pienso en las actividades familiares o personales cuando estoy en mi trabajo", "frecuencia"),
                        "22" to Pregunta("Pienso que mis responsabilidades familiares afectan mi trabajo", "frecuencia"),
                        "23" to Pregunta("Mi trabajo permite que desarrolle nuevas habilidades", "frecuencia"),
                        "24" to Pregunta("En mi trabajo puedo aspirar a un mejor puesto", "frecuencia"),
                        "25" to Pregunta("Durante mi jornada de trabajo puedo tomar pausas cuando las necesito", "frecuencia"),
                        "26" to Pregunta("Puedo decidir cuánto trabajo realizo durante la jornada laboral", "frecuencia"),
                        "27" to Pregunta("Puedo decidir la velocidad a la que realizo mis actividades en mi trabajo", "frecuencia"),
                        "28" to Pregunta("Puedo cambiar el orden de las actividades que realizo en mi trabajo", "frecuencia"),
                        "29" to Pregunta("Los cambios que se presentan en mi trabajo dificultan mi labor", "frecuencia"),
                        "30" to Pregunta("Cuando se presentan cambios en mi trabajo se tienen en cuenta mis ideas o aportaciones", "frecuencia"),
                        "31" to Pregunta("Me informan con claridad cuáles son mis funciones", "frecuencia"),
                        "32" to Pregunta("Me explican claramente los resultados que debo obtener en mi trabajo", "frecuencia"),
                        "33" to Pregunta("Me explican claramente los objetivos de mi trabajo", "frecuencia"),
                        "34" to Pregunta("Me informan con quién puedo resolver problemas o asuntos de trabajo", "frecuencia"),
                        "35" to Pregunta("Me permiten asistir a capacitaciones relacionadas con mi trabajo", "frecuencia"),
                        "36" to Pregunta("Recibo capacitación útil para hacer mi trabajo", "frecuencia"),
                        "37" to Pregunta("Mi jefe ayuda a organizar mejor el trabajo", "frecuencia"),
                        "38" to Pregunta("Mi jefe tiene en cuenta mis puntos de vista y opiniones", "frecuencia"),
                        "30" to Pregunta("Mi jefe me comunica a tiempo la información relacionada con el trabajo", "frecuencia"),
                        "40" to Pregunta("La orientación que me da mi jefe me ayuda a realizar mejor mi trabajo", "frecuencia"),
                        "41" to Pregunta("Mi jefe ayuda a solucionar los problemas que se presentan en el trabajo", "frecuencia"),
                        "42" to Pregunta("Puedo confiar en mis compañeros de trabajo", "frecuencia"),
                        "43" to Pregunta("Entre compañeros solucionamos los problemas de trabajo de forma respetuosa", "frecuencia"),
                        "44" to Pregunta("En mi trabajo me hacen sentir parte del grupo", "frecuencia"),
                        "45" to Pregunta("Cuando tenemos que realizar trabajo de equipo los compañeros colaboran", "frecuencia"),
                        "46" to Pregunta("Mis compañeros de trabajo me ayudan cuando tengo dificultades", "frecuencia"),
                        "47" to Pregunta("Me informan sobre lo que hago bien en mi trabajo", "frecuencia"),
                        "48" to Pregunta("La forma como evalúan mi trabajo en mi centro de trabajo me ayuda a mejorar mi desempeño", "frecuencia"),
                        "49" to Pregunta("En mi centro de trabajo me pagan a tiempo mi salario", "frecuencia"),
                        "50" to Pregunta("El pago que recibo es el que merezco por el trabajo que realizo", "frecuencia"),
                        "51" to Pregunta("Si obtengo los resultados esperados en mi trabajo me recompensan o reconocen", "frecuencia"),
                        "52" to Pregunta("Las personas que hacen bien el trabajo pueden crecer laboralmente", "frecuencia"),
                        "53" to Pregunta("Considero que mi trabajo es estable", "frecuencia"),
                        "54" to Pregunta("En mi trabajo existe continua rotación de personal", "frecuencia"),
                        "55" to Pregunta("Siento orgullo de laborar en este centro de trabajo", "frecuencia"),
                        "56" to Pregunta("Me siento comprometido con mi trabajo", "frecuencia"),
                        "57" to Pregunta("En mi trabajo puedo expresarme libremente sin interrupciones", "frecuencia"),
                        "58" to Pregunta("Recibo críticas constantes a mi persona y/o trabajo", "frecuencia"),
                        "59" to Pregunta("Recibo burlas, calumnias, difamaciones, humillaciones o ridiculizaciones", "frecuencia"),
                        "60" to Pregunta("Se ignora mi presencia o se me excluye de las reuniones de trabajo y en la toma de decisiones", "frecuencia"),
                        "61" to Pregunta("Se manipulan las situaciones de trabajo para hacerme parecer un mal trabajador", "frecuencia"),
                        "62" to Pregunta("Se ignoran mis éxitos laborales y se atribuyen a otros trabajadores", "frecuencia"),
                        "63" to Pregunta("Me bloquean o impiden las oportunidades que tengo para obtener ascenso o mejora en mi trabajo", "frecuencia"),
                        "64" to Pregunta("He presenciado actos de violencia en mi centro de trabajo", "frecuencia"),
                    )
                ),
                Seccion(
                    mapOf(
                        "01" to Pregunta("En mi trabajo debo brindar servicio a clientes o usuarios", "si_no")
                    )
                ),
                Seccion(
                    mapOf(
                        "65" to Pregunta("Atiendo clientes o usuarios muy enojados", "frecuencia"),
                        "66" to Pregunta("Mi trabajo me exige atender personas muy necesitadas de ayuda o enfermas", "frecuencia"),
                        "67" to Pregunta("Para hacer mi trabajo debo demostrar sentimientos distintos a los míos", "frecuencia"),
                        "68" to Pregunta("Mi trabajo me exige atender situaciones de violencia", "frecuencia")
                    )
                ),
                Seccion(
                    mapOf(
                        "01" to Pregunta("Soy jefe de otros trabajadores", "si_no")
                    )
                ),
                Seccion(
                    mapOf(
                        "69" to Pregunta("Comunican tarde los asuntos de trabajo","frecuencia"),
                        "70" to Pregunta("Dificultan el logro de los resultados del trabajo","frecuencia"),
                        "71" to Pregunta("Cooperan poco cuando se necesita","frecuencia"),
                        "72" to Pregunta("Ignoran las sugerencias para mejorar su trabajo", "frecuencia")

                    )
                )
            )
        )
    )
}