---
title: "Intel·ligència Artificial - Pràctica 2"
author: [Marc Sànchez Pifarré, GEINF (UDG-EPS)]
date: 2019-04-03
subject: "Udg - Eps"
tags: [Drons apagafocs]
subtitle: "Tutor de la pràctica : Llorenç Burgas"
titlepage: true
titlepage-color: 06386e
titlepage-text-color: FFFFFF
titlepage-rule-height: 4
...

# Introducció

En aquest informe destacaré els mètodes més rellevants així com els patrons i el plantejament per a la solució del problema.

## Funcionament global

### Petita adaptació.

Referent a l'estat del codi inicial s'hi ha fet una petita adaptació. S'ha generat una herència per part de tots els elements que intervenen al tauler d'una classe Position.

Aquesta herència s'ha realitzat per aconseguir una generització a l'hora de fer el tractament de les distàncies. Tots els elements comparteixen els mètodes de càlcul euclidià.

### Operacions dels Robots ()

Cada un dels elements mòbils, Robot, pot moure's en 8 sentits. NO, N, NE, E, SE, S, SO. Existeix una sola operació moure que en funció d'un destí es desplaça cap aquest destí 1 casella tant en horitzontal, com en diagonal, com en vertical. **Es preveuen les colisions** dels robots desviant la trajectòria dels mateixos. Definim crash com la casella on hi van a parar 2 drons de cop.

```{java}
/**
 * En funció d'una serie de posicions conflictives intentem desviar el dron.
 */
public void setNextPosition(List<Position> crashes){
    SortedSet<Voyage> desviaments = new TreeSet<Voyage>();
    desviaments.add(new Voyage(new myRobot(from.x, from.y+1, this.from.escena), target));
    desviaments.add(new Voyage(new myRobot(from.x, from.y-1, this.from.escena), target));
    desviaments.add(new Voyage(new myRobot(from.x-1, from.y, this.from.escena), target));
    desviaments.add(new Voyage(new myRobot(from.x+1, from.y, this.from.escena), target));
    desviaments.add(new Voyage(new myRobot(from.x+1, from.y+1, this.from.escena), target));
    desviaments.add(new Voyage(new myRobot(from.x+1, from.y-1, this.from.escena), target));

    desviaments.add(new Voyage(new myRobot(from.x-1, from.y+1, this.from.escena), target));
    desviaments.add(new Voyage(new myRobot(from.x-1, from.y-1, this.from.escena), target));
    Voyage desv = desviaments.first();
    while(!desviaments.isEmpty() && crashes.contains(desv.from)){
        desviaments.remove(desv);
        desv = desviaments.first();
    }
    nextX = desv.from.x;
    nextY = desv.from.y;
}
```

El que es fa és generar tots els possibles moviments del dron i sel·leccionar-ne un que no entri en conflicte amb cap crash.

### Viatge

Definim viatge que la trajectòria que realitza un Robot des del punt en el que està fins al seu target. És una classe que conté 4 camps, el robot que es mou, l'objectiu a assolir, la següent posició x i la següent posició y.

Amb aquesta classe es domina el que seria el tractament dels possibles destins als que pot arribar un Robot. A l'hora d'ordenar els viatges s'utilitza una llista i el mètode Collections.sort() que crida al mètode compareTo del Voiage. Dins del compareTo s'implementa el càlcul de punts que estipula lo bo que és aquest viatge.


### Càlcul de la puntuació.

Tant per el mètode cooperatiu com el competitiu s'estipula la mateixa puntuació per definir l'objectiu a apagar. Per realitzar el càlcul es mira tots els focs i s'ordenen per una puntuació.

```{java}

@Override
public int compareTo(Voyage o) {       
    return (calcul(this)).compareTo(calcul(o));        
}

/**
 * Es separa en un mètode per poder-lo cridar des d'altres punts.
 */
private Double calcul(Voyage o){
    Double dist = o.target.getEuclidian(o.from);
    Double arbres = from.escena.nombreArbresVoltant(o.target.x, o.target.y);
    if(arbres == 0){
        dist += 13;
    }
    else if(arbres > 0 && arbres < 3){
        dist += 5;
    }
    else if(arbres >= 3 && arbres < 6){
        dist += 2;
    }
    // Prioritzo per sobre de tot els que tenen més de 5 arbres
    return dist;
}
```

La distància euclidiana del viatge es desa a la variable dist mentre que la variable arbres conté el nombre d'arbres que poden ser cremats per el foc target. El càlcul de la puntuació està pensat de la següent manera :

Seguin les següents premises.

- El Robot realitza el càlcul a cada pas que dóna i avança de 1 en 1 per tant a mesura que vagi avançant el càlcul serà més ajustat.
- Si la distància és molt llarga en relació a la puntuació assolida per el nombre d'arbres ens interessa que primi la ditància que el nombre d'arbres.

Al final la suma d'un valor extret del nombre d'arbres i la distància euclidiana serà el que permetrà al Robot decidir el seu destí. Inicialment S'utilitzava la série de fibonacci, número per número, és a dir, dist + fibonacci(arbres). La precisió aportava desviació i s'ha acabat ajustant a utiltizar la proporció de fibonacci peró saltant-se els parells i l'1.

- 0 arbres = fibonacci(7) = 13
- 1 o 2 arbres = fibonacci(5) = 5
- 3, 4 o 5 arbres = fibonacci(3) = 2
- 6 o més arbres = fibonacci(0) = 0

Llavors a mesura que es vagi acostant al foc anirà descartant els focs que no son perillosos i primarà anar a apagar els focs que estan amb més risc de propagació.

**S'utiltiza la série de fibonacci per que el nombre més alt de la série també serà el que tindrá la major distància amb el seu antecessor, també la vaig utilitzar a la pràctica del quarto.**

## Funcionament Cooperatiu

Per establir el protocol de comunicació el que s'ha fet ha sigut dotar els drons d'una "bústia de viatges". Cada dron implementa les interfícies Subject i Observer que li permeten enviar i rebre els viatges que han programat els drons que té al voltant. Per fer-ho a cada pas que han de donar es subscriuen els drons i es dessubscriuen al final del torn.

La idea és subscric, notifico, dessubscric.

Cada cop que un Robot decideix viatjar cap a un target ho comunica als demés. Per tant quan un dron comença a calcular ja sap on aniran els altres drons que ja han pres una decisió, d'aquesta manera pot decidir si anar cap al mateix lloc on van o no i realitzar el càlcul també entrant els destins i els moviments dels altres a l'equació (crashes).

## Funcionament competitiu

Es deshabilita el pas de missatges. S'utilitza la mateixa estratègia per definir els destins sense tenir en compte la decisió dels altres ja que l'objectiu dels drons ha de ser apagar tots els focs intentant apagar més que els demés peró que es prioiritzin les propagacions és l'objectiu principal i ha d'estar juntament amb el càlcul de les distàncies.

## Guinyo a en Jordi Regincós.

S'ha definit un patró strategy per realitzar el canvi de comportament entre cooperatiu i competitiu, s'ha afegit un botó a sota de tot de la botonera que permet en temps d'execució canviar el comportament. Per defecte sempre comencen en cooperatiu, un click canvia a !cooperatiu és a dir a competitiu i si es torna a clicar canvien a cooperatiu, i així successivament.
