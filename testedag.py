from airflow import DAG
from airflow.providers.amazon.aws.operators.emr_create_job_flow import EmrCreateJobFlowOperator
from datetime import datetime

default_args = {
    'owner': 'airflow',
    'depends_on_past': False,
    'email_on_failure': False,
    'email_on_retry': False,
    'retries': 1,
    'retry_delay': timedelta(minutes=5)
}

dag = DAG(
    'emr_cluster_with_managed_scaling',
    default_args=default_args,
    description='A DAG to create an EMR cluster with managed scaling policy',
    schedule_interval='@once',
    start_date=datetime(2022, 1, 1),
    catchup=False
)

# Define a configuração do cluster EMR com a política de dimensionamento gerenciado
emr_cluster_config = {
    "Name": "your-emr-cluster",
    "ReleaseLabel": "emr-6.0.0",  # Versão do EMR
    "Applications": [{"Name": "Spark"}],  # Aplicativos a serem instalados no cluster
    "Instances": {
        "InstanceGroups": [
            {
                "Name": "Master node",
                "Market": "ON_DEMAND",  # Tipo de instância (ON_DEMAND ou SPOT)
                "InstanceRole": "MASTER",
                "InstanceType": "m5.xlarge",
                "InstanceCount": 1
            },
            {
                "Name": "Core nodes",
                "Market": "ON_DEMAND",
                "InstanceRole": "CORE",
                "InstanceType": "m5.xlarge",
                "InstanceCount": 2
            }
        ],
        "Ec2KeyName": "your-keypair-name",  # Nome da chave EC2
        "KeepJobFlowAliveWhenNoSteps": True,
        "TerminationProtected": False
    },
    "VisibleToAllUsers": True,
    "ManagedScalingPolicy": {  # Política de dimensionamento gerenciado
        "ComputeLimits": {
            "MinimumCapacityUnits": 2,
            "MaximumCapacityUnits": 10
        }
    }
}

# Define o operador para criar o cluster EMR
create_cluster_task = EmrCreateJobFlowOperator(
    task_id='create_emr_cluster',
    dag=dag,
    aws_conn_id='aws_default',  # Identificador de conexão AWS
    emr_conn_id='emr_default',  # Identificador de conexão EMR
    job_flow_overrides=emr_cluster_config
)
