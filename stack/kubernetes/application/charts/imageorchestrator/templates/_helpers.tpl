{{- define "dima-imageorchestrator.service.name" -}}
{{- .Release.Name | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "imagerotator.baseUrl" -}}
http://{{ template "dima-imagerotator.service.name" . }}:8080
{{- end -}}

{{- define "imageflip.baseUrl" -}}
http://{{ template "dima-imageflip.service.name" . }}:8080
{{- end -}}

{{- define "imagegrayscale.baseUrl" -}}
http://{{ template "dima-imagegrayscale.service.name" . }}:8080
{{- end -}}

{{- define "imageholder.baseUrl" -}}
http://{{ template "dima-imageholder.service.name" . }}:8080
{{- end -}}

{{- define "imageresize.baseUrl" -}}
http://{{ template "dima-imageresize.service.name" . }}:8080
{{- end -}}