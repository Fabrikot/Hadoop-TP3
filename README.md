# Hadoop Collaborative Filtering

## Commandes

**Pour setup le projet, executez les commandes suivantes :**

En premier lieu il faut construire l'image docker :
```
docker build -t fabrikot/tp-final . //dans le répertoire deploy
```

Puis run dans un container, monter les volumes et exécuter le container :
```
docker run --rm --name=fabrikot-tp-final -p 8088:8088 -p 9870:9870 -p 9864:9864 -v ./data:/data -v ./jars:/jars -d fabrikot/tp-final
docker exec -it fabrikot-tp-final bash
```
Ensuite, il faut initialiser le HDFS :
```
hdfs dfs -mkdir -p /fdeville/input //créer le répertoire input
hdfs dfs -put /data/relationships/data.txt /fdeville/input/ //copier le fichier data.txt dans le répertoire input
```

**Pour exécuter le job Hadoop, utilisez la commande suivante :**

```
hadoop jar /jars/job1.jar /fdeville/input/data.txt /output/job1
```