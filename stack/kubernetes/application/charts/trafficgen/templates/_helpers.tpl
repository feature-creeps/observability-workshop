{{- define "dima-trafficgen.service.name" -}}
{{- .Release.Name | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "dima-trafficgen.imageholder.baseUrl" -}}
{{- if .Values.imageholder.baseUrl -}}
{{- .Values.imageholder.baseUrl -}}
{{- else -}}
http://{{ template "dima-imageholder.service.name" . }}:8080
{{- end -}}
{{- end -}}

{{- define "dima-trafficgen.imageorchestrator.baseUrl" -}}
{{- if .Values.imageorchestrator.baseUrl -}}
{{- .Values.imageorchestrator.baseUrl -}}
{{- else -}}
http://{{ template "dima-imageorchestrator.service.name" . }}:8080
{{- end -}}
{{- end -}}