# Aplicación Libros con Spring Boot CI/CD con AWS

Aplicación CRUD libros diseñada y desarrollada con Java 17, Maven y una base de datos H2, la aplicación ofrece opciones básicas de listado, registro, eliminación y filtrado por identificador de autores.
Todo el proceso de desarrollo, integración y despliegue de la aplicación se llevó a cabo con prácticas de Integración Continua y Despliegue Continuo (CI/CD) utilizando servicios de AWS. Desde el control de versiones con AWS CodeCommit, la construcción automatizada con AWS CodeBuild, hasta la implementación sin interrupciones con AWS CodeDeploy y la automatización del flujo de trabajo con AWS CodePipeline, la aplicación se desarrolla y despliega de manera eficiente y confiable en instancias EC2, garantizando una experiencia sin problemas para los usuarios.

## Descripción Flujo CI/CD

A continuación se detalla los pasos realizados desde el almacenamiento en el repositorio hasta el despliegue en instancias EC2.

### [Administración de identidades | IAM ](https://aws.amazon.com/es/iam/?trk=3cfe03ed-c5b4-45aa-b6e1-27fc8d2b4f35&sc_channel=ps&s_kwcid=AL!4422!10!71468491682381!71469018664105&ef_id=6e1e4aae96561686e3f62f60dca8e66a:G:s)

1. Para la instancia EC2 crear un rol que permita la conexión con AWS CodeDeploy y System Manager.
   
   1. En la opción roles de la consola AWS seleccionar "Create role".
   2. En la opción Trusted entity type seleccionar "AWS Service" y en Use Case seleccionar "EC2".
   3. En la opción Permissions policies buscar y seleccionar las políticas AmazonEC2RoleforAWSCodeDeploy y AmazonSSMManagedInstanceCore.
   4. En la opción nombre del rol ingresar "EC2InstanceRoleDevops", verificar que los cambios sean correctos y crear rol.

2. Para ejecutar CodeDeploy crear un rol
   
   1. En la opción roles de la consola AWS seleccionar "Create role".
   2. En la opción Trusted entity type seleccionar "AWS Service" y en Use Case seleccionar "CodeDeploy".
   3. En la opción Permissions policies verificar la política AWSCodeDeployRole.
   4. En la opción nombre del rol ingresar "CodeDeployRole", verificar que los cambios sean correctos y crear rol.

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
