# Caseirinha Real - Deployment Instructions

This document provides a step-by-step guide to deploy the **Caseirinha Real** web application (a cake store application). The deployment process involves using **Vagrant**, **Google Cloud Platform (GCP)**, **Terraform**, and **Ansible** to set up the infrastructure, configure Kubernetes, and start the application.



## 1. Prerequisites

- [Vagrant](https://developer.hashicorp.com/vagrant/downloads)
- [Google Cloud CLI](https://cloud.google.com/sdk/docs/install) installed on your **local machine** (for `gcloud` commands)
- A valid GCP project (with project ID)
- Terraform installed locally (or on the management VM)
- SSH keys (will be generated during the deployment)



## 2. Setup and Vagrant

1. **Navigate to the Setup directory** in your local environment:

   ```bash
   cd Setup
   ```

2. **Start the Vagrant virtual machine:**

   ```bash
   vagrant up
   ```

3. **SSH into the Vagrant machine:**
   ```bash
   vagrant ssh
   ```

You are now inside the management VM where you will execute further commands



## 3. GCP Configuration

1. **Navigate to the GCP Cloud directory** inside the VM:

```bash
 cd labs/Setup/gcpcloud
```

2. **Authenticate with your Google Cloud account:**

```bash
 gcloud auth login
```

Follow the prompt in your browser to complete the authentication



## 4. Update Terraform Variables

1. **Open the file** `terraform.gcp-variables.ttf`

2. **Set your GCP project ID:**

   ```bash
   'variable "GCP_PROJECT_ID" {
   default = "agisit-2425-website-XXXXX-7657685"
   }'
   ```

3. You should check your `GCP_ZONE` and if it doesn't work you should change them and also the `GCP_REGION`



## 5. Initialize Terraform and Generate SSH Key

1. **Initialize Terraform:**

```bash
terraform init
```

2. **Generate an SSH key** (press ENTER through the prompts):

```bash
ssh-keygen -t rsa -b 2048
```

After completion, you will see the `The key's randomart image is:` (example):

```bash
+--[ED25519 256]--+
|..o              |
|.+               |
| .E              |
|.. .     +o o    |
| .+.    S+=+     |
| .... o.*+o*     |
|     oo*.o+oo.   |
|     o o=+.oo+   |
|     .o.oo==B++  |
+----[SHA256]-----+
```

## 6. Terraform Plan and Apply

1. **Preview the changes** Terraform will make:

```bash
tarraform plan
```

2. **Apply the Terraform configuration:**

```bash
terraform apply
```

- Terraform will ask for confirmation. Type `yes` to proceed

- This process will create the necessary GCP infrastucture (one master node and multiple worker nodes).

Upon successful completion, Terraform will output something similiar to:

```bash
master = "34.118.4.170"
master_ssh = "https://www.googleapis.com/compute/v1/projects/your-gcp-project-id/zones/europe-central2-a/instances/master"
worker_IPs = tolist([
  "worker1 = 34.118.25.221",
  "worker2 = 34.118.13.250",
  "worker3 = 34.116.245.78",
  "worker4 = 34.116.143.160",
  "worker5 = 34.118.110.146",
])
```



## 7. Update Hosts File

1. **Gather the IPs** from the Terraform output.

2. **Edit the hosts file** inisde the management VM (the one you accessed with `vagrant ssh`):

```bash
sudo nano /etc/hosts
```

3. Add the appropriate entries for the master and worker nodes, for example:

```bash
34.118.4.170   master
34.118.25.221  worker1
34.118.13.250  worker2
34.116.245.78  worker3
34.116.143.160 worker4
34.118.110.146 worker5
```

4. **Save and exit** the file.

## 8. Ansible Playbooks

In the **management VM**, run the following Ansible playbooks in **order**:

1. **Configure Nodes:**

```bash
ansible-playbook ansible-gcp-configure-nodes.yml
```

2. **Install Kubernetes on All Nodes:**

```bash
ansible-playbook ansible-k8s-install.yml

```

3. **Create the K8s Cluster:**

```bash
ansible-playbook ansible-create-cluster.yml

```

4. **Join Worker Nodes to the Cluster:**

```bash
ansible-playbook ansible-workers-join.yml

```

5. **Start Deployment:**

```bash
ansible-playbook ansible-start-deployment.yml

```

Once these playbooks finish, your Kubernetes cluster will be up and ready for the application.



## 9. Define External IP for the Web Server

### 9.1. **Log in to the Master Node (in GCP)**

   From your local environment got to the Google Cloud Console:

   1. In the **Compute Engine > VM instances** section, find the **Master** instance

   2. **Use the SSH command** shown in the console. It should look like:

   ```bash
      gcloud compute ssh --zone "europe-central2-a" "master" --project "your-gcp-project-id"
   ```

   3. You are now inside the **Master node.**

### 9.2. **Locate the Ingress Controller Pod**

   1. **Get the pods** in the `ingress-nginx` namespace:

   ```bash
      sudo kubectl get pods -n ingress-nginx
   ```

   2. **Copy the pod name** that contains `ingress-nginx-controller`.

   3. **Describe the pod** to see which node it's running on:

   ```bash
      sudo kubectl describe pod [NAME_OF_YOUR_INGRESS_CONTROLLER_POD] -n ingress-nginx
   ```

   Look fot the `Node:` value in the output.

### 9.3. **Get the External** IP of That Worker

   1. Back in the **GCP Console**, go to **Compute Engine > VM instances** again.
   2. Find the worker node that matches the **Node** name from the previous step.
   3. **Copy the External IP** of that worker.

### 9.4. **Patch the Ingress Service with the External IP**

   1. Run the following command **from the Master node** to patch the ingress-nginx-controller service:

   ```bash
      sudo kubectl patch svc ingress-nginx-controller \
      -p '{"spec":{"externalIPs":["EXTERNAL_IP_HERE"]}}' \
      -n ingress-nginx
   ```
   Replace `EXTERNAL_IP_HERE` with the IP you copied from the GCP Console.

   2. **Verify the service:**

   ```bash
      sudo kubectl get services -n ingress-nginx
   ```

   Look for the `ingress-nginx-controller` external IP and **PORT**. It will likely show something like 80:PORT_NUMBER.



## 10. **Update Deployment Configuration**

   1. Open your **development.yml** (or similar file containing your application’s ingress settings).
   2. **Replace** the placeholder `EXTERNAL_IP_HERE` with the actual IP from the previous step.
   3. **Replace** `PORT_NUMBER` with the port you found (80:`PORT_NUMBER`).
   
## 11. **Access the Application**

   Finally, **open** your web browser and **navigate** to:

   ```bash
      https://EXTERNAL_IP_HERE:PORT_NUMBER
   ```

   You should see the **Caseirinha Real** application up and running!