# Aplicación Libros con Spring Boot CI/CD con AWS

Aplicación CRUD libros diseñada y desarrollada con Java 17, Maven y una base de datos H2, la aplicación ofrece opciones básicas de listado, registro, eliminación y filtrado por identificador de autores.
Todo el proceso de desarrollo, integración y despliegue de la aplicación se llevó a cabo con prácticas de Integración Continua y Despliegue Continuo (CI/CD) utilizando servicios de AWS. Desde el control de versiones con AWS CodeCommit, la construcción automatizada con AWS CodeBuild, hasta la implementación sin interrupciones con AWS CodeDeploy y la automatización del flujo de trabajo con AWS CodePipeline, la aplicación se desarrolla y despliega de manera eficiente y confiable en instancias EC2, garantizando una experiencia sin problemas para los usuarios.

## Descripción Flujo CI/CD

A continuación se detalla los pasos realizados desde el almacenamiento en el repositorio hasta el despliegue en instancias EC2.

### [Administración de identidades | IAM ](https://aws.amazon.com/es/iam/?trk=3cfe03ed-c5b4-45aa-b6e1-27fc8d2b4f35&sc_channel=ps&s_kwcid=AL!4422!10!71468491682381!71469018664105&ef_id=6e1e4aae96561686e3f62f60dca8e66a:G:s)

1. Crear el rol **EC2InstanceRoleDevops** con las políticas correspondientes de AWS para permitir el uso del agente CloudWatch, agente CodeDeploy y System Manager

   1. En la opción roles de la consola AWS seleccionar "Create role".

   2. En la opción Trusted entity type seleccionar "AWS Service" y en Use Case seleccionar "EC2".

   3. En la opción Permissions policies buscar y seleccionar las políticas:

      * **AmazonSSMManagedInstanceCore**, política que permite a SSM ejecutar comandos de forma remota, aplicar parches y configurar software en instancias EC2.

      * **AmazonEC2RoleforAWSCodeDeploy**, política que permite a AWS CodeDeploy interactuar con instancias EC2 durante las implementaciones.

      * **CloudWatchAgentServerPolicy**, política que permite al agente de Amazon CloudWatch recopilar y enviar de forma segura métricas y registros a CloudWatch en su nombre.

      * **CloudWatchAgentAdminPolicy**, política que permite realizar tareas relacionadas con el agente, como instalar, configurar y administrar el agente de CloudWatch en instancias EC2. Además, permite que el agente escriba registros, métricas y configuraciones en el disco de la instancia antes de enviarlos a CloudWatch.

   4. En la opción nombre del rol ingresar "EC2InstanceRoleDevops", verificar que los cambios sean correctos y crear rol.

2. Para ejecutar CodeDeploy crear un rol

   1. En la opción roles de la consola AWS seleccionar "Create role".
   2. En la opción Trusted entity type seleccionar "AWS Service" y en Use Case seleccionar "CodeDeploy".
   3. En la opción Permissions policies verificar la política AWSCodeDeployRole.
   4. En la opción nombre del rol ingresar "CodeDeployRole", verificar que los cambios sean correctos y crear rol.

3. Para que el agente de CloudWatch registre métricas en CloudWatch y para que se comunique con Amazon EC2 y AWS Systems Manager

   1. En la opción roles de la consola AWS seleccionar "Create role".
   2.

### [EC2 ](https://docs.aws.amazon.com/es_es/ec2/)

1. Ingresar a la consola EC2, seleccionar "Launch an instance" y asignar a la instancia el nombre  "**BookStoreDevops**"

2. En la opción Imágenes de aplicaciones y sistemas operativos seleccionar Amazon Linux y AMI de Amazon Linux 2023 que es apto para la capa gratuita.

3. En la opción tipo de instancia seleccionar t2.micro, que es apto para la capa gratuita.

4. En la opción par de claves (inicio de sesión), para este laboratorio, basta con elegir continuar sin un par de claves.

5. En la opción configuraciones de red:

   1. Seleccionar asignar automáticamente la IP pública y elegir habilitar.

   2. Crear un grupo de seguridad con las reglas de entrada

      ```yaml
      SSH      ==> 0.0.0.0/0
      HTTP     ==> 0.0.0.0/0
      ```

6. En la opción detalles avanzados, seleccionar como perfil de instancia IAM el rol que se creó "EC2InstanceRoleDevops"

### [AWS CodeCommit](https://aws.amazon.com/es/codecommit/)

1. Crear el repositorio "book_store_devops" en la consola de AWS.

2. Clonar el repositorio mediante HTTPS o SSH en nuestro entorno.

3. Subir el código fuente de la aplicación al repositorio remoto, además de los archivos buildspec.yml, appspec.yml y la carpeta scripts que serán necesarios en la etapa de CodeBuild y CodeDeploy respectivamente.

### [AWS CodeBuild](https://aws.amazon.com/es/codebuild/)

1. En la opción Build Project de la consola de AWS hacer clic en la opción "Create Project"

2. Ingresar el nombre del proyecto "book_devops_build"

3. En source seleccionar "AWS CodeCommit", seleccionando el nombre del repositorio creado en la etapa anterior e indicando el nombre de la rama, en este caso "main".

4. En Environment seleccionar el sistema operativo Linux, con runtime Standard e imagen aws/codebuild/amazonlinux2-x86-64-standard:5.0. Además, seleccionar la opción para crear un "New service role".

5. En Buildspec seleccionar la opción "Use a buildspec file", es en este paso en que indicamos que en el repositorio existe un archivo con el nombre buildspec.yml y se desea que se ejecute las instrucciones que se detallan dentro.

### [Amazon CodeDeploy](https://aws.amazon.com/es/codedeploy/)

1. Seleccionar la opción Applications en el menú de opciones lateral de la consola de AWS.

2. Seleccionar la opción Create Application e ingresar el nombre "book_devops_deploy" y en la opción Compute platform seleccionar EC2/On-premises.

3. Seleccionar la aplicación que se ha creado con el nombre book_devops_deploy y dentro seleccionar la opción Create deployment group.

4. En la opción Deployment group name ingresar "book_devops_group_deploy".

5. En la opción Service role seleccionar el rol creado "CodeDeployRole".

6. En la opción Deployment type dejar el valor por defecto en "In-place".

7. En la opción Environment configuration seleccionar "Amazon EC2 instances" y agregar el tag group con los valores

   ```
   Key: Name
   Value: BookStoreDevops
   ```

8. En la opción Deployment settings seleccionar "CodeDeployDefault.OneAtATime"

9. En la opción Load balancer desmarcar la opción "Enable load balancing" ya que sólo se ejecutará una instancia.

### [AWS CodePipeline](https://aws.amazon.com/es/codepipeline/)

1. Seleccionar la opción Create pipeline en la consola de AWS.
2. Seleccionar la opción Crear canalización e ingresar el nombre "book_devops_pipeline" y en la opción Compute platform seleccionar EC2/On-premises.
3. En la opción Rol del servicio seleccionar "Nuevo rol de servicio" y otorgar un nombre o dejar el que se genera por defecto.
4. En la etapa de Origen seleccionar como proveedor "AWS CodeCommit", en nombre seleccionar "book_store_devops" y en la rama la opción "main".
5. En la etapa de compilación seleccionar como proveedor de compilación "AWS CodeBuild", la región en la que creamos y seleccionar el nombre "book_devops_build" .
6. En la etapa de implementación seleccionar "AWS CodeDeploy", la región en la que fue creada, el nombre "book_devops_deploy" y el grupo de implementación "book_devops_group_deploy".
7. En la última etapa revisamos todos los detalles y se procede a crear la canalización.

### [AWS CloudWatch](https://aws.amazon.com/es/cloudwatch/)

1. Verificar en la instancia la instalación del agente cloudwatch

   ```
   amazon-cloudwatch-agent-ctl -a status
   ```

2. La salida de la consola mostrará el mensaje

   ```bash
   -bash: amazon-cloudwatch-agent-ctl: command not found
   ```

3. Abrir AWS Systems Manager y seleccionar Parameter Store

   1. Elegir la opción Crear parámetro e introducir un nombre que inicie con el prefijo "AmazonCloudWatch", en este caso será "AmazonCloudWatch-Logs-Metrics"
   2. Elegir la Capa Estándar
   3. En Tipo seleccionar Cadena (String)
   4. En Tipo de datos seleccionar Text
   5. En valor introducir el contenido del siguiente archivo y seleccionar crear.

   ```yaml
   {
      "agent":{
         "metrics_collection_interval":1,
         "logfile":"/opt/aws/amazon-cloudwatch-agent/logs/amazon-cloudwatch-agent.log"
      },
      "metrics":{
         "namespace":"MyCustomNamespace",
         "metrics_collected":{
            "mem":{
               "measurement":[
                  "mem_used_percent"
               ],
               "metrics_collection_interval":1
            },
            "disk":{
               "measurement":[
                  "used_percent"
               ],
               "resources":[
                  "/"
               ]
            }
         },
         "append_dimensions":{
            "InstanceId":"${aws:InstanceId}"
         }
      },
      "logs":{
         "logs_collected":{
            "files":{
               "collect_list":[
                  {
                     "file_path":"/home/ec2-user/book_store_devops/target/logs/application.log",
                     "log_group_name":"ec2-devops-logs",
                     "log_stream_name":"{instance_id}",
                     "timezone":"UTC"
                  }
               ]
            }
         }
      }
   }
   ```

4. En AWS Systems Manager seleccionar en Administración de Nodos la opción Distribuidor

   1. Entre las opciones de propiedad de Amazon, buscar y elegir AmazonCloudWatchAgente, luego seleccionar Instalar una vez.
   2. La opción de ejecutar comando se abre y verificar que este seleccionado AWS-ConfigureAWSPackage
   3. En la opción parámetros de comando seleccionar la acción Install y en Name AmazonCloudWatchAgent
   4. En la opción destinos elegir instancias de forma manual y seleccionar la instancia EC2 "BookStoreDevops".
   5. En opciones de salida desmarcar la casilla "Habilitar la escritura en un bucket de S3".
   6. Seleccionar ejecutar comando.
   7. En de la instancia EC2 ejecutar nuevamente el comando

      ```
      amazon-cloudwatch-agent-ctl -a status
      ```
   8. La salida de la consola mostrará un mensaje similar al siguiente, con ello se comprueba la instalación del agente CloudWatch.

      ```json
      {
         "status": "stopped",
         "starttime": "",
         "configstatus": "not configured",
         "version": "1.300034.0b498"
      }
      ```

5. En AWS Systems Manager seleccionar en Administración de Nodos la opción Run Command

   1. En la opción Documento de comando, buscar y seleccionar AmazonCloudWatch-ManageAgent.
   2. En la opción Parámetros de comando, para Optional Configuration Source seleccionar "ssm" y en Optional Configuration Location introducir el nombre del parameter store creado previamente "AmazonCloudWatch-Logs-Metrics"
   3. En la opción destinos elegir instancias de forma manual y seleccionar la instancia EC2 "BookStoreDevops".
   4. En opciones de salida desmarcar la casilla "Habilitar la escritura en un bucket de S3".
   5. Seleccionar ejecutar comando.
   6. En de la instancia EC2 ejecutar nuevamente el comando
      ```
      amazon-cloudwatch-agent-ctl -a status
      ```
   7. La salida de la consola mostrará un mensaje similar al siguiente, con ello se comprueba la ejecución del agente CloudWatch.
      ```bash
      {
       "status": "running",
       "starttime": "2024-03-18T21:04:05+00:00",
       "configstatus": "configured",
       "version": "1.300034.0b498"
      }
      ```
   8. En la consola de AWS CloudWatch seleccionar grupo de registros, en la lista elegir "ec2-devops-logs" que es el nombre del grupo de logs creado.
   9. En la consola de AWS CloudWatch seleccionar Métricas, todas las métricas y elegir "MyCustomNamespace" que es el nombre del espacio de métricas creado.
