{{- define "dima-imageorchestrator.service.name" -}}
{{- .Release.Name | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "dima-imageorchestrator.imagerotator.baseUrl" -}}
http://{{ template "dima-imagerotator.service.name" . }}:8080
{{- end -}}

{{- define "dima-imageorchestrator.imageflip.baseUrl" -}}
http://{{ template "dima-imageflip.service.name" . }}:8080
{{- end -}}

{{- define "dima-imageorchestrator.imagegrayscale.baseUrl" -}}
http://{{ template "dima-imagegrayscale.service.name" . }}:8080
{{- end -}}

{{- define "dima-imageorchestrator.imageholder.baseUrl" -}}
http://{{ template "dima-imageholder.service.name" . }}:8080
{{- end -}}

{{- define "dima-imageorchestrator.imageresize.baseUrl" -}}
http://{{ template "dima-imageresize.service.name" . }}:8080
{{- end -}}