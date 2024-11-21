# How to deploy to docker container
1. mvn clean install
2. docker build -t backend-service:latest .
3. docker tag backend-service:latest dickanirwansyah1996/backend-service:latest
4. docker push dickanirwansyah1996/backend-service:latest

# How to orchestration using kubernetes
1. create base deployment.yaml
2. kubectl apply -k overlays/dev
3. kubectl apply -k overlays/prod
4. kubectl create secret generic jwt-secret --from-literal=secret-key=7f312164792f0ab848c987b7e0980807faddf42cc19d284e0822ce40888a30b59f4701bee6574a7eacdc8d6498f970462b25db28c512e8a6bbf218deb6294db5

# Give access ip4 mysql
CREATE USER 'root'@'muhamads-air.lan' IDENTIFIED BY 'rootroot';
GRANT ALL PRIVILEGES ON db_backend_service.* TO 'root'@'muhamads-air.lan';
FLUSH PRIVILEGES;

SELECT user, host FROM mysql.user WHERE user = 'root';

# Integrated with argocd
1. kubectl create namespace argocd
2. kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml
3. kubectl port-forward svc/argocd-server -n argocd 8080:443
4. kubectl get secret argocd-initial-admin-secret -n argocd -o jsonpath="{.data.password}" | base64 -d && echo
