{{- define "dima-imageholder.service.name" -}}
{{- .Release.Name | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "dima-imageholder.imagethumbnail.baseUrl" -}}
http://{{ template "dima-imagethumbnail.service.name" . }}:8080
{{- end -}}
