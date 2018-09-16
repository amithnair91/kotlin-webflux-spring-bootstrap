IMG_NAME := bootstrap-service


.PHONY: build
build:
	@gradle build

.PHONY: image
image:
	@docker build -f $(CURDIR)/docker/Dockerfile -t $(IMG_NAME) .

.PHONY: run
run:
	@docker run --rm -d -p 8080:8080 --name=bootstrap-service bootstrap-service:latest

.PHONY: rmc
rmc:
	@docker rm -f bootstrap-service