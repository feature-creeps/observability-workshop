{{- define "dima-trafficgen.service.name" -}}
{{- .Release.Name | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "imageholder.baseUrl" -}}
http://{{ template "dima-imageholder.service.name" . }}:8080
{{- end -}}

{{- define "dima-imageorchestrator.service.name" -}}
http://{{ template dima-imageorchestrator.service.name" . }}:8080
    {{ .Release.Name }}-imageorchestrator-service
{{- end -}}