{{- define "dima-imagethumbnail.service.name" -}}
{{- .Release.Name | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "imageholder.baseUrl" -}}
http://{{ template "dima-imageholder.service.name" . }}:8080
{{- end -}}
