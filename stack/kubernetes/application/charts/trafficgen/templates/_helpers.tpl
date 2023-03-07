{{- define "dima-trafficgen.service.name" -}}
{{- .Release.Name | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "imageholder.baseUrl" -}}
http://{{ template "dima-imageholder.service.name" . }}:8080
{{- end -}}

{{- define "imageorchestrator.baseUrl" -}}
http://{{ template "dima-imageorchestrator.service.name" . }}:8080
{{- end -}}