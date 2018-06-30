# FileSystem Crawler for Elasticsearch

Welcome to the FS Crawler for [Elasticsearch](https://elastic.co/)

This crawler helps to index documents from your local file system and over SSH.
It crawls your file system and index new files, update existing ones and removes old ones.

You need to install a version matching your Elasticsearch version:

|    Elasticsearch   |  FS Crawler | Released |                                       Docs                                   |
|--------------------|-------------|----------|------------------------------------------------------------------------------|
| 2.x, 5.x, 6.x      | 2.5-SNAPSHOT|          |See below                                                                     |
| 2.x, 5.x, 6.x      | **2.4**     |2017-08-11|[2.4](https://github.com/dadoonet/fscrawler/blob/fscrawler-2.4/README.md)     |
| 2.x, 5.x, 6.x      | 2.3         |2017-07-10|[2.3](https://github.com/dadoonet/fscrawler/blob/fscrawler-2.3/README.md)     |
| 1.x, 2.x, 5.x      | 2.2         |2017-02-03|[2.2](https://github.com/dadoonet/fscrawler/blob/fscrawler-2.2/README.md)     |
| 1.x, 2.x, 5.x      | 2.1         |2016-07-26|[2.1](https://github.com/dadoonet/fscrawler/blob/fscrawler-2.1/README.md)     |
|    es-2.0          | 2.0.0       |2015-10-30|[2.0.0](https://github.com/dadoonet/fscrawler/blob/fscrawler-2.0.0/README.md) |

## Build and Quality Status

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/fr.pilato.elasticsearch.crawler/fscrawler/badge.svg?style=flat-square)](https://maven-badges.herokuapp.com/maven-central/fr.pilato.elasticsearch.crawler/fscrawler/)
[![Travis](https://secure.travis-ci.org/dadoonet/fscrawler.png)](http://travis-ci.org/dadoonet/fscrawler)

[![Lines](https://sonarcloud.io/api/project_badges/measure?project=fr.pilato.elasticsearch.crawler:fscrawler&metric=ncloc)](https://sonarcloud.io/project/issues?id=fr.pilato.elasticsearch.crawler%3Afscrawler)
[![Duplicated Lines](https://sonarcloud.io/api/project_badges/measure?project=fr.pilato.elasticsearch.crawler:fscrawler&metric=duplicated_lines_density)](https://sonarcloud.io/project/issues?id=fr.pilato.elasticsearch.crawler%3Afscrawler)
[![Maintainability](https://sonarcloud.io/api/project_badges/measure?project=fr.pilato.elasticsearch.crawler:fscrawler&metric=sqale_rating)](https://sonarcloud.io/project/issues?id=fr.pilato.elasticsearch.crawler%3Afscrawler)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=fr.pilato.elasticsearch.crawler:fscrawler&metric=sqale_index)](https://sonarcloud.io/project/issues?id=fr.pilato.elasticsearch.crawler%3Afscrawler)
[![Reliability](https://sonarcloud.io/api/project_badges/measure?project=fr.pilato.elasticsearch.crawler:fscrawler&metric=reliability_rating)](https://sonarcloud.io/project/issues?id=fr.pilato.elasticsearch.crawler%3Afscrawler)

[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=fr.pilato.elasticsearch.crawler:fscrawler&metric=vulnerabilities)](https://sonarcloud.io/project/issues?id=fr.pilato.elasticsearch.crawler%3Afscrawler&resolved=false&types=VULNERABILITY)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=fr.pilato.elasticsearch.crawler:fscrawler&metric=bugs)](https://sonarcloud.io/project/issues?id=fr.pilato.elasticsearch.crawler%3Afscrawler)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=fr.pilato.elasticsearch.crawler:fscrawler&metric=alert_status)](https://sonarcloud.io/project/issues?id=fr.pilato.elasticsearch.crawler%3Afscrawler)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=fr.pilato.elasticsearch.crawler:fscrawler&metric=code_smells)](https://sonarcloud.io/project/issues?id=fr.pilato.elasticsearch.crawler%3Afscrawler)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=fr.pilato.elasticsearch.crawler:fscrawler&metric=coverage)](https://sonarcloud.io/project/issues?id=fr.pilato.elasticsearch.crawler%3Afscrawler)

# Table of content

* [Installation guide](#installation-guide)
    * [Download fscrawler](#download-fscrawler)
    * [Upgrade fscrawler](#upgrade-fscrawler)
* [User guide](#user-guide)
    * [Getting Started](#getting-started)
    * [Searching for docs](#searching-for-docs)
    * [Crawler options](#crawler-options)
    * [Starting with a REST gateway](#starting-with-a-rest-gateway)
    * [Supported formats](#supported-formats)
* [Administration guide](#administration-guide)
    * [CLI options](#cli-options)
    * [JVM Settings](#jvm-settings)
    * [Job file specification](#job-file-specification)
        * [Local FS settings](#local-fs-settings)
        * [SSH settings](#ssh-settings)
        * [Elasticsearch settings](#elasticsearch-settings)
        * [REST service](#rest-service)
* [Tips and tricks](#tips-and-tricks)
* [License](#license)

# Installation Guide

## Download fscrawler

FS Crawler binary is available on [Maven Central](https://repo1.maven.org/maven2/fr/pilato/elasticsearch/crawler/fscrawler/).
Just download the latest release (or any other specific version you want to try).

The filename ends with `.zip`.

For example, if you wish to download [fscrawler-2.4](https://repo1.maven.org/maven2/fr/pilato/elasticsearch/crawler/fscrawler/2.4/fscrawler-2.4.zip):

```sh
wget https://repo1.maven.org/maven2/fr/pilato/elasticsearch/crawler/fscrawler/2.4/fscrawler-2.4.zip
unzip fscrawler-2.4.zip
```

The distribution contains:

```
$ tree
.
├── LICENSE
├── NOTICE
├── README.md
├── bin
│   ├── fscrawler
│   └── fscrawler.bat
└── lib
    ├── ... All needed jars
```

Note that you can also download a SNAPSHOT version
[from sonatype](https://oss.sonatype.org/content/repositories/snapshots/fr/pilato/elasticsearch/crawler/fscrawler/2.5-SNAPSHOT/)
without needing to build it by yourself.

## Upgrade fscrawler

It can happen that you need to [upgrade a mapping](#upgrading-an-existing-mapping) before starting fscrawler after a
version upgrade.
Read carefully the following update instructions.

To update fscrawler, just download the new version, unzip it in another directory and launch it as usual.
It will still pick up settings from the configuration directory. Of course, you need to stop first the existing
running instances.

### Upgrade to 2.2

* fscrawler comes with new default mappings for files. They have better defaults as they consume less disk space
and CPU at index time. You should remove existing files in `~/.fscrawler/_default/_mappings` before starting the new
version so default mappings will be updated. If you modified manually mapping files, apply the modification you made
on sample files.

* `excludes` is now set by default for new jobs to `["~*"]`. In previous versions, any file or directory containing a
`~` was excluded. Which means that if in your jobs, you are defining any exclusion rule, you need to add `*~*` if
you want to get back the exact previous behavior.

* If you were indexing `json` or `xml` documents with the `filename_as_id` option set, we were previously removing the
suffix of the file name, like indexing `1.json` was indexed as `1`. With this new version, we don't remove anymore the
suffix. So the `_id` for your document will be now `1.json`.

### Upgrade to 2.3

* fscrawler comes with new mapping for folders. The change is really tiny so you can skip this step if you wish.
We basically removed `name` field in the folder mapping as it was unused.

* The way FSCrawler computes now `path.virtual` for docs has changed. It now includes the filename.
Instead of `/path/to` you will now get `/path/to/file.txt`.

* The way FSCrawler computes now `virtual` for folders is now consistent with what you can see for folders.

* `path.encoded` in documents and `encoded` in folders have been removed as not needed by FSCrawler after all.

* [OCR](#ocr-integration) is now properly activated for PDF documents. This can be time, cpu and memory consuming though.
You can disable explicitly it by setting `fs.pdf_ocr` to `false`.

* All dates are now indexed in elasticsearch in UTC instead of without any time zone. For example, we were indexing
previously a date like `2017-05-19T13:24:47.000`. Which was producing bad results when you were located in a time zone
other than UTC. It's now indexed as `2017-05-19T13:24:47.000+0000`.

* In order to be compatible with the coming 6.0 elasticsearch version, we need to get rid of types as only one type
per index is still supported. Which means that we now create index named `job_name` and `job_name_folder` instead
of one index `job_name` with two types `doc` and `folder`. If you are upgrading from FSCrawler 2.2, it requires that
you reindex your existing data either by deleting the old index and running again FSCrawler or by using the
[reindex API](https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-reindex.html) as follows:

```
# Create folder index job_name_folder based on existing folder data
POST _reindex
{
  "source": {
    "index": "job_name",
    "type": "folder"
  },
  "dest": {
    "index": "job_name_folder"
  }
}
# Remove old folder data from job_name index
POST job_name/folder/_delete_by_query
{
  "query": {
    "match_all": {}
  }
}
```

Note that you will need first to create the right settings and mappings so you can then run the reindex job.
You can do that by launching `bin/fscrawler job_name --loop 0`.

Better, you can run `bin/fscrawler job_name --upgrade` and let FSCrawler do all that for you. Note that this can take
a loooong time.

Also please be aware that some APIs used by the upgrade action are only available from elasticsearch 2.3 (reindex) or
elasticsearch 5.0 (delete by query). If you are running an older version than 5.0 you need first to upgrade elasticsearch.

This procedure only applies if you did not set previously `elasticsearch.type` setting (default value was `doc`).
If you did, then you also need to reindex the existing documents to the default `doc` type as per elasticsearch 6.0:

```
# Copy old type doc to the default doc type
POST _reindex
{
  "source": {
    "index": "job_name",
    "type": "your_type_here"
  },
  "dest": {
    "index": "job_name",
    "type": "doc"
  }
}
# Remove old type data from job_name index
POST job_name/your_type_here/_delete_by_query
{
  "query": {
    "match_all": {}
  }
}
```

But note that this last step can take a very loooong time and will generate a lot of IO on your disk.
It might be easier in such case to restart fscrawler from scratch.

* As seen in the previous point, we now have 2 indices instead of a single one. Which means that `elasticsearch.index`
setting has been split to `elasticsearch.index` and `elasticsearch.index_folder`. By default, it's set to the
crawler name and the crawler name plus `_folder`. Note that the `upgrade` feature performs that change for you.

* fscrawler has removed now mapping files `doc.json` and `folder.json`. Mapping for doc is merged within `_settings.json`
file and folder mapping is now part of `_settings_folder.json`. Which means you can remove old files to avoid confusion.
You can simply remove existing files in `~/.fscrawler/_default` before starting the new version so default
files will be created again.

### Upgrade to 2.4

* No specific step needed. Just note that mapping changed as we support more metadata. Might be useful to run
similar steps as for 2.2 upgrade.

### Upgrade to 2.5

* A bug was causing a lot of data going over the wire each time FSCrawler was running. To fix this issue, we changed the
default mapping and we set `store: true` on field `file.filename`. If this field is not stored and `remove_deleted` is
`true` (default), FSCrawler will fail while crawling your documents. You need to create the new mapping accordingly
and reindex your existing data either by deleting the old index and running again FSCrawler or by using the
[reindex API](https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-reindex.html) as follows:

```
# Backup old index data
POST _reindex
{
  "source": {
    "index": "job_name"
  },
  "dest": {
    "index": "job_name_backup"
  }
}
# Remove job_name index
DELETE job_name
```

Restart FSCrawler with the following command. It will just create the right mapping again.

```sh
$ bin/fscrawler job_name --loop 0
```

Then restore old data:


```
POST _reindex
{
  "source": {
    "index": "job_name_backup"
  },
  "dest": {
    "index": "job_name"
  }
}
# Remove backup index
DELETE job_name_backup
```

The default mapping changed for FSCrawler for `meta.raw.*` fields. Might be better to reindex your data.

# User Guide

## Getting Started

You need to have at least **Java 1.8.** and have properly configured `JAVA_HOME` to point to your Java installation
directory. For example on MacOS you can define in your `~/.bash_profile` file:

```sh
export JAVA_HOME=`/usr/libexec/java_home -v 1.8`
```

Start FS crawler with:

```sh
bin/fscrawler job_name
```

FS crawler will read a local file (default to `~/.fscrawler/{job_name}/_settings.json`).
If the file does not exist, FS crawler will propose to create your first job.

```sh
$ bin/fscrawler job_name
18:28:58,174 WARN  [f.p.e.c.f.FsCrawler] job [job_name] does not exist
18:28:58,177 INFO  [f.p.e.c.f.FsCrawler] Do you want to create it (Y/N)?
y
18:29:05,711 INFO  [f.p.e.c.f.FsCrawler] Settings have been created in [~/.fscrawler/job_name/_settings.json]. Please review and edit before relaunch
```

Create a directory named `/tmp/es` or `c:\tmp\es`, add some files you want to index in it and start again:

```sh
$ bin/fscrawler --config_dir ./test job_name
18:30:34,330 INFO  [f.p.e.c.f.FsCrawlerImpl] Starting FS crawler
18:30:34,332 INFO  [f.p.e.c.f.FsCrawlerImpl] FS crawler started in watch mode. It will run unless you stop it with CTRL+C.
18:30:34,682 INFO  [f.p.e.c.f.FsCrawlerImpl] FS crawler started for [job_name] for [/tmp/es] every [15m]
```

If you did not create the directory, FS crawler will complain until you fix it:

```
18:30:34,683 WARN  [f.p.e.c.f.FsCrawlerImpl] Error while indexing content from /tmp/es: /tmp/es doesn't exists.
```

You can also run FS crawler without arguments. It will give you the list of existing jobs and will allow you to
choose one:

```
$ bin/fscrawler
18:33:00,624 INFO  [f.p.e.c.f.FsCrawler] No job specified. Here is the list of existing jobs:
18:33:00,629 INFO  [f.p.e.c.f.FsCrawler] [1] - job_name
18:33:00,629 INFO  [f.p.e.c.f.FsCrawler] Choose your job [1-1]...
1
18:33:06,151 INFO  [f.p.e.c.f.FsCrawlerImpl] Starting FS crawler
```

## Searching for docs

This is a common use case in elasticsearch, we want to search for something! ;-)

```json
GET docs/doc/_search
{
  "query" : {
    "match" : {
        "_all" : "I am searching for something !"
    }
  }
}
```


## Crawler options

By default, FS crawler will read your file from `/tmp/es` every 15 minutes. You can change those settings by
modifying `~/.fscrawler/{job_name}/_settings.json` file where `{job_name}` is the name of the job you just created.

```json
{
  "name" : "job_name",
  "fs" : {
    "url" : "/path/to/data/dir",
    "update_rate" : "15m"
  }
}
```

You can change also `update_rate` to watch more or less frequently for changes.

If you just want FS crawler to run once and exit, run it with `--loop` option:

```sh
$ bin/fscrawler job_name --loop 1
18:47:37,487 INFO  [f.p.e.c.f.FsCrawlerImpl] Starting FS crawler
18:47:37,854 INFO  [f.p.e.c.f.FsCrawlerImpl] FS crawler started for [job_name] for [/tmp/es] every [15m]
...
18:47:37,855 INFO  [f.p.e.c.f.FsCrawlerImpl] FS crawler is stopping after 1 run
18:47:37,959 INFO  [f.p.e.c.f.FsCrawlerImpl] FS crawler [job_name] stopped
```

If you have already ran FS crawler and want to restart (which means reindex existing documents),
use the `--restart` option:

```sh
$ bin/fscrawler job_name --loop 1 --restart
```

You will find more information about settings in the following sections:

* [CLI options](#cli-options)
* [Local FS settings](#local-fs-settings)
* [SSH settings](#ssh-settings)
* [Elasticsearch settings](#elasticsearch-settings)

## Starting with a REST gateway

FS crawler can be a nice gateway to elasticsearch if you want to upload binary documents
and index them into elasticsearch without writing by yourself all the code to extract data
and communicate with elasticsearch.

To start FS crawler with the REST service, use the `--rest` option. A good idea is also to combine it
with `--loop 0` so you won't index local files but only listen to incoming REST requests:

```sh
$ bin/fscrawler job_name --loop 0 --rest
18:55:37,851 INFO  [f.p.e.c.f.FsCrawlerImpl] Starting FS crawler
18:55:39,237 INFO  [f.p.e.c.f.FsCrawlerImpl] FS crawler Rest service started on [http://127.0.0.1:8080/fscrawler]
```

Check the service is working with:

```sh
curl http://127.0.0.1:8080/fscrawler/
```

It will give you back a JSON document.

The you can start uploading your binary files:

```sh
echo "This is my text" > test.txt
curl -F "file=@test.txt" "http://127.0.0.1:8080/fscrawler/_upload"
```

It will index the file into elasticsearch and will give you back the elasticsearch URL
for the created document, like:

```json
{
  "ok" : true,
  "filename" : "test.txt",
  "url" : "http://127.0.0.1:9200/fscrawler-rest-tests_doc/doc/dd18bf3a8ea2a3e53e2661c7fb53534"
}
```

You will find more information about REST settings in the following section:

* [REST settings](#rest-settings)

## Supported formats

FS crawler supports all formats [Tika version 1.17 supports](http://tika.apache.org/1.17/formats.html#Supported_Document_Formats), like:

* HTML
* Microsoft Office
* Open Office
* PDF
* Images
* MP3
* ...




# Administration guide

Once the crawler is running, it will write status information and statistics in:

* `~/.fscrawler/{job_name}/_settings.json`
* `~/.fscrawler/{job_name}/_status.json`

It means that if you stop the job at some point, FS crawler will restart it from where it stops.

FS crawler will also store default mappings and index settings for elasticsearch in `~/.fscrawler/_default/_mappings`:

* `1/_settings.json`: for elasticsearch 1.x series document index settings
* `1/_settings_folder.json`: for elasticsearch 1.x series folder index settings
* `2/_settings.json`: for elasticsearch 2.x series document index settings
* `2/_settings_folder.json`: for elasticsearch 2.x series folder index settings
* `5/_settings.json`: for elasticsearch 5.x series document index settings
* `5/_settings_folder.json`: for elasticsearch 5.x series folder index settings
* `6/_settings.json`: for elasticsearch 6.x series document index settings
* `6/_settings_folder.json`: for elasticsearch 6.x series folder index settings

Read [Mapping](#mapping) for more information.

## CLI options

* `--help` displays help
* `--silent` runs in silent mode. No output is generated.
* `--debug` runs in debug mode.
* `--trace` runs in trace mode (more verbose than debug).
* `--config_dir` defines directory where jobs are stored instead of default `~/.fscrawler`.
* `--username` defines the username to use when using an secured version of elasticsearch cluster. Read
[Using Credentials](#using-credentials). (From 2.2)
* `--upgrade_mapping` tries to upgrade existing mappings for documents and folders. Read
[Upgrading an existing mapping](#upgrading-an-existing-mapping). (From 2.2)
* `--loop x` defines the number of runs we want before exiting  (From 2.2):

    * `X` where X is a negative value means infinite, like `-1` (default)
    * `0` means that we don't run any crawling job (useful when used with rest).
    * `X` where X is a positive value is the number of runs before it stops.

If you want to scan your hard drive only once, run with `--loop 1`.

* `--restart` restart a job from scratch (From 2.2). See below.
* `--rest` starts the REST service (From 2.2):

If you want to run the [REST Service](#rest-service) without scanning your hard drive, launch with:

```sh
bin/fscrawler --rest --loop 0
```

You can tell FS crawler that it must restart from the beginning by using `--restart` option:

```sh
bin/fscrawler job_name --restart
```

In that case, the `{job_name}/_status.json` file will be removed.

## JVM Settings

If you want to provide JVM settings, like defining memory allocated to FS Crawler, you can define
a system property named `FS_JAVA_OPTS`:

```sh
FS_JAVA_OPTS="-Xmx521m -Xms521m" bin/fscrawler
```

## Configuring an external logger configuration file

If you want to define an external log4j2.xml file, you can use the `log4j.configurationFile` JVM parameter which
you can define in `FS_JAVA_OPTS` variable if you wish:

```sh
FS_JAVA_OPTS="-Dlog4j.configurationFile=path/to/log4j2.xml" bin/fscrawler
```

You can use [the default log4j2.xml file](https://github.com/dadoonet/fscrawler/blob/master/src/main/resources/log4j2.xml)
as an example to start with.


## Job file specification

The job file must comply to the following `json` specifications:

```json
{
  "name" : "job_name",
  "fs" : {
    "url" : "/path/to/docs",
    "update_rate" : "5m",
    "includes" : [ "*.doc", "*.xls" ],
    "excludes" : [ "resume.doc" ],
    "json_support" : false,
    "filename_as_id" : true,
    "add_filesize" : true,
    "remove_deleted" : true,
    "add_as_inner_object" : false,
    "store_source" : true,
    "index_content" : true,
    "indexed_chars" : "10000.0",
    "attributes_support" : false,
    "raw_metadata" : true,
    "xml_support" : false,
    "index_folders" : true,
    "lang_detect" : false,
    "continue_on_error" : false,
    "pdf_ocr" : true,
    "ocr" : {
      "language" : "eng",
      "path": "/path/to/tesseract/if/not/available/in/PATH",
      "data_path": "/path/to/tesseract/tessdata/if/needed"
    }
  },
  "server" : {
    "hostname" : "localhost",
    "port" : 22,
    "username" : "dadoonet",
    "password" : "password",
    "protocol" : "SSH",
    "pem_path" : "/path/to/pemfile"
  },
  "elasticsearch" : {
    "nodes" : [ {
      "host" : "127.0.0.1",
      "port" : 9200,
      "scheme" : "HTTP"
    } ],
    "index" : "docs",
    "bulk_size" : 1000,
    "flush_interval" : "5s",
    "username" : "elastic",
    "password" : "password"
  },
  "rest" : {
    "scheme" : "HTTP",
    "host" : "127.0.0.1",
    "port" : 8080,
    "endpoint" : "fscrawler"
  }
}
```

Here is a list of existing top level settings:

|       Name      |                       Documentation                         |
|-----------------|-------------------------------------------------------------|
| `name`          | [the job name](#the-most-simple-crawler) (mandatory field)  |
| `fs`            | [Local FS settings](#local-fs-settings)                     |
| `elasticsearch` | [Elasticsearch settings](#elasticsearch-settings)           |
| `server`        | [SSH settings](#ssh-settings)                               |
| `rest`          | [REST settings](#rest-settings)                             |

### The most simple crawler

You can define the most simple crawler job by writing a `~/.fscrawler/test/_settings.json` file as follow:

```json
{
  "name" : "test"
}
```

This will scan every 15 minutes all documents available in `/tmp/es` dir and will index them into `test_doc` index.
It will connect to an elasticsearch cluster running on `127.0.0.1`, port `9200`.

**Note**: `name` is a mandatory field.

### Local FS settings

Here is a list of Local FS settings (under `fs.` prefix)`:

|               Name               | Default value |                                 Documentation                                     |
|----------------------------------|---------------|-----------------------------------------------------------------------------------|
| `fs.url`                         | `"/tmp/es"`   | [Root directory](#root-directory)                                                 |
| `fs.update_rate`                 | `"15m"`       | [Update Rate](#update-rate)                                                       |
| `fs.includes`                    | `null`        | [Includes and Excludes](#includes-and-excludes)                                   |
| `fs.excludes`                    | `["~*"]`      | [Includes and Excludes](#includes-and-excludes)                                   |
| `fs.json_support`                | `false`       | [Indexing JSon docs](#indexing-json-docs)                                         |
| `fs.xml_support`                 | `false`       | [Indexing XML docs](#indexing-xml-docs) (from 2.2)                                |
| `fs.add_as_inner_object`         | `false`       | [Add as Inner Object](#add-as-inner-object)                                       |
| `fs.index_folders`               | `true`        | [Index folders](#index-folders) (from 2.2)                                        |
| `fs.attributes_support`          | `false`       | [Adding file attributes](#adding-file-attributes)                                 |
| `fs.raw_metadata`                | `true`        | [Disabling raw metadata](#disabling-raw-metadata)                                 |
| `fs.filename_as_id`              | `false`       | [Using Filename as `_id`](#using-filename-as-elasticsearch-_id)                   |
| `fs.add_filesize`                | `true`        | [Disabling file size field](#disabling-file-size-field)                           |
| `fs.remove_deleted`              | `true`        | [Ignore deleted files](#ignore-deleted-files)                                     |
| `fs.store_source`                | `false`       | [Storing binary source document](#storing-binary-source-document-base64-encoded)  |
| `fs.index_content`               | `true`        | [Ignore content](#ignore-content)                                                 |
| `fs.lang_detect`                 | `false`       | [Language detection](#language-detection) (from 2.2)                              |
| `fs.continue_on_error`           | `false`       | [Continue on File Permission Error](#continue-on-error) (from 2.3)                |
| `fs.pdf_ocr`                     | `true`        | [Run OCR on PDF documents](#ocr-integration) (from 2.3)                           |
| `fs.indexed_chars`               | `100000.0`    | [Extracted characters](#extracted-characters)                                     |
| `fs.checksum`                    | `null`        | [File Checksum](#file-checksum)
| `fs.custom_tika_parsers`         | `null`        | [Custom Tika Parsers](#custom-tika-parsers)   |

#### Root directory

Define `fs.url` property in your `~/.fscrawler/test/_settings.json` file:

```json
{
  "name" : "test",
  "fs" : {
    "url" : "/path/to/data/dir"
  }
}
```

For Windows users, use a form like `c:/tmp` or `c:\\tmp`.

#### Update rate

By default, `update_rate` is set to `15m`. You can modify this value using any compatible
[time unit](https://www.elastic.co/guide/en/elasticsearch/reference/current/common-options.html#time-units).

For example, here is a 15 minutes update rate:

```json
{
  "name": "test",
  "fs": {
    "update_rate": "15m"
  }
}
```

Or a 3 hours update rate:

```json
{
  "name": "test",
  "fs": {
    "update_rate": "3h"
  }
}
```

`update_rate` is the pause duration between the last time we read the file system and another run.
Which means that if you set it to `15m`, the next scan will happen on 15 minutes after the end of
the current scan, whatever its duration.

#### Includes and excludes

Let's say you want to index only docs like `*.doc` and `*.pdf` but `resume*`. So `resume_david.pdf` won't be indexed.

Define `fs.includes` and `fs.excludes` properties in your `~/.fscrawler/test/_settings.json` file:

```json
{
  "name" : "test",
  "fs": {
    "includes": [
      "*.doc",
      "*.pdf"
    ],
    "excludes": [
      "resume*"
    ]
  }
}
```

It also applies to directory names. So if you want to ignore `.ignore` dir, just add `.ignore` as an excluded name.
Note that `includes` does not apply to directory names but only to filenames.

By default, FS crawler will exclude files starting with `~`.

#### Indexing JSon docs

If you want to index JSon files directly without parsing with Tika, you can set `json_support` to `true`.
JSon contents will be stored directly under _source. If you need to keep JSon documents synchronized to the index,
set option [Add as Inner Object](#add-as-inner-object) which stores additional metadata and the JSon contents under
field `object`.

```json
{
  "name" : "test",
  "fs" : {
    "json_support" : true
  }
}
```

Of course, if you did not define a mapping before launching the crawler, Elasticsearch will auto guess the mapping.

#### Indexing XML docs

If you want to index XML files and convert them to JSON, you can set `xml_support` to `true`.
The content of XML files will be added directly under _source. If you need to keep XML documents synchronized to the
index, set option [Add as Inner Object](#add-as-inner-object) which stores additional metadata and the XML contents
under field `object`.

```json
{
  "name" : "test",
  "fs" : {
    "xml_support" : true
  }
}
```

Of course, if you did not define a mapping before launching the crawler, Elasticsearch will auto guess the mapping.

#### Add as Inner Object

The default settings store the contents of json and xml documents directly onto the _source element of elasticsearch
documents. Thereby, there is no metadata about file and path settings, which are necessary to determine if a document
is deleted or updated.
New files will however be added to the index, (determined by the file timestamp).

If you need to keep json or xml documents synchronized to elasticsearch, you should set this option.

```json
{
  "name" : "test",
  "fs" : {
    "add_as_inner_object" : true
  }
}
```

#### Index folders

By default FS Crawler will index folder names in the folder index.
If you don't want to index those folders, you can set `index_folders` to `false`.

Note that in that case, FS Crawler won't be able to detect removed folders so any document has been indexed
in elasticsearch, it won't be removed when you remove or move the folder away.

```json
{
  "name" : "test",
  "fs" : {
    "index_folders" : false
  }
}
```

##### Dealing with multiple types and multiple dirs

If you have more than one type, create as many crawlers as types:

`~/.fscrawler/test_type1/_settings.json`:

```json
{
  "name": "test_type1",
  "fs": {
	"url": "/tmp/type1",
	"json_support" : true
  },
  "elasticsearch": {
    "index": "mydocs1",
    "index_folder": "myfolders1"
  }
}
```

`~/.fscrawler/test_type2/_settings.json`:

```json
{
  "name": "test_type2",
  "fs": {
	"url": "/tmp/type2",
	"json_support" : true
  },
  "elasticsearch": {
    "index": "mydocs2",
    "index_folder": "myfolders2"
  }
}
```

`~/.fscrawler/test_type3/_settings.json`:

```json
{
  "name": "test_type3",
  "fs": {
	"url": "/tmp/type3",
	"xml_support" : true
  },
  "elasticsearch": {
    "index": "mydocs3",
    "index_folder": "myfolders3"
  }
}
```

##### Dealing with multiple types within the same dir

You can also index many types from one single dir using two crawlers scanning the same dir and by setting
`includes` parameter:

`~/.fscrawler/test_type1.json`:

```json
{
  "name": "test_type1",
  "fs": {
	"url": "/tmp",
    "includes": [ "type1*.json" ],
	"json_support" : true
  },
  "elasticsearch": {
    "index": "mydocs1",
    "index_folder": "myfolders1"
  }
}
```

`~/.fscrawler/test_type2.json`:

```json
{
  "name": "test_type2",
  "fs": {
	"url": "/tmp",
    "includes": [ "type2*.json" ],
	"json_support" : true
  },
  "elasticsearch": {
    "index": "mydocs2",
    "index_folder": "myfolders2"
  }
}
```

`~/.fscrawler/test_type3.json`:

```json
{
  "name": "test_type3",
  "fs": {
	"url": "/tmp",
    "includes": [ "*.xml" ],
	"xml_support" : true
  },
  "elasticsearch": {
    "index": "mydocs3",
    "index_folder": "myfolders3"
  }
}
```

#### Using filename as elasticsearch `_id`

Please note that the document `_id` is always generated (hash value) from the filename to avoid issues with
special characters in filename.
You can force to use the `_id` to be the filename using `filename_as_id` attribute:

```json
{
  "name" : "test",
  "fs" : {
    "filename_as_id" : true
  }
}
```

#### Adding file attributes

If you want to add file attributes such as `attributes.owner` and `attributes.group`, you can set `attributes_support` to `true`.

```json
{
  "name" : "test",
  "fs" : {
    "attributes_support" : true
  }
}
```

#### Disabling raw metadata

By default, FS Crawler will extract all found metadata within `meta.raw` object.
If you want to disable this feature, you can set `raw_metadata` to `false`.

```json
{
  "name" : "test",
  "fs" : {
    "raw_metadata" : false
  }
}
```

Generated raw metadata depends on the file format itself.

For example, a PDF document could generate:

* `"date" : "2016-07-07T08:37:42Z"`
* `"pdf:PDFVersion" : "1.5"`
* `"xmp:CreatorTool" : "Microsoft Word"`
* `"Keywords" : "keyword1, keyword2"`
* `"access_permission:modify_annotations" : "true"`
* `"access_permission:can_print_degraded" : "true"`
* `"subject" : "Test Tika Object"`
* `"dc:creator" : "David Pilato"`
* `"dcterms:created" : "2016-07-07T08:37:42Z"`
* `"Last-Modified" : "2016-07-07T08:37:42Z"`
* `"dcterms:modified" : "2016-07-07T08:37:42Z"`
* `"dc:format" : "application/pdf; version=1.5"`
* `"title" : "Test Tika title"`
* `"Last-Save-Date" : "2016-07-07T08:37:42Z"`
* `"access_permission:fill_in_form" : "true"`
* `"meta:save-date" : "2016-07-07T08:37:42Z"`
* `"pdf:encrypted" : "false"`
* `"dc:title" : "Test Tika title"`
* `"modified" : "2016-07-07T08:37:42Z"`
* `"cp:subject" : "Test Tika Object"`
* `"Content-Type" : "application/pdf"`
* `"X-Parsed-By" : "org.apache.tika.parser.DefaultParser"`
* `"creator" : "David Pilato"`
* `"meta:author" : "David Pilato"`
* `"dc:subject" : "keyword1, keyword2"`
* `"meta:creation-date" : "2016-07-07T08:37:42Z"`
* `"created" : "Thu Jul 07 10:37:42 CEST 2016"`
* `"access_permission:extract_for_accessibility" : "true"`
* `"access_permission:assemble_document" : "true"`
* `"xmpTPg:NPages" : "2"`
* `"Creation-Date" : "2016-07-07T08:37:42Z"`
* `"access_permission:extract_content" : "true"`
* `"access_permission:can_print" : "true"`
* `"meta:keyword" : "keyword1, keyword2"`
* `"Author" : "David Pilato"`
* `"access_permission:can_modify" : "true"`

Where a MP3 file would generate:

* `"xmpDM:genre" : "Vocal"`
* `"X-Parsed-By" : "org.apache.tika.parser.DefaultParser"`
* `"creator" : "David Pilato"`
* `"xmpDM:album" : "FS Crawler"`
* `"xmpDM:trackNumber" : "1"`
* `"xmpDM:releaseDate" : "2016"`
* `"meta:author" : "David Pilato"`
* `"xmpDM:artist" : "David Pilato"`
* `"dc:creator" : "David Pilato"`
* `"xmpDM:audioCompressor" : "MP3"`
* `"title" : "Test Tika"`
* `"xmpDM:audioChannelType" : "Stereo"`
* `"version" : "MPEG 3 Layer III Version 1"`
* `"xmpDM:logComment" : "Hello but reverted"`
* `"xmpDM:audioSampleRate" : "44100"`
* `"channels" : "2"`
* `"dc:title" : "Test Tika"`
* `"Author" : "David Pilato"`
* `"xmpDM:duration" : "1018.775146484375"`
* `"Content-Type" : "audio/mpeg"`
* `"samplerate" : "44100"`

The `meta.raw.*` fields have a default mapping applied:

```json
{
  "type": "text",
  "fields": {
    "keyword": {
      "type": "keyword",
      "ignore_above": 256
    }
  }
}
```

If you want specifically tell elasticsearch to use a date type or a numeric type for some fields, you need
to modify the default template provided by FSCrawler.

Note that dots in metadata names will be replaced by a `:`. For example `PTEX.Fullbanner` will be indexed as
`PTEX:Fullbanner`.

#### Disabling file size field

By default, FS crawler will create a field to store the original file size in octets.
You can disable it using `add_filesize' option:

```json
{
  "name" : "test",
  "fs" : {
    "add_filesize" : false
  }
}
```

#### Ignore deleted files

If you don't want to remove indexed documents when you remove a file or a directory, you can
set `remove_deleted` to `false` (default to `true`):


```json
{
  "name" : "test",
  "fs" : {
    "remove_deleted" : false
  }
}
```

#### Ignore content

If you don't want to extract file content but only index filesystem metadata such as filename, date, size and path,
you can set `index_content` to `false` (default to `true`):


```json
{
  "name" : "test",
  "fs" : {
    "index_content" : false
  }
}
```

#### Continue on Error

By default FS Crawler will immediately stop indexing if he hits a Permission denied exception.
If you want to just skip this File and continue with the rest of the directory tree you can 
set `continue_on_error` to `true` (default to `false`):


```json
{
  "name" : "test",
  "fs" : {
    "continue_on_error" : true
  }
}
```


#### Language detection

From FS crawler 2.2, you can ask for language detection using `lang_detect` option:

```json
{
  "name" : "test",
  "fs" : {
    "lang_detect" : true
  }
}
```

In that case, a new field named `meta.language` is added to the generated JSon document.

If you are using elasticsearch 5.0 or superior, you can use this value to send your document to a specific index
using a [Node Ingest pipeline](#using-ingest-node-pipeline).

For example, you can define a pipeline named `langdetect` with:

```sh
PUT _ingest/pipeline/langdetect
{
  "description" : "langdetect pipeline",
  "processors" : [
    {
      "set": {
        "field": "_index",
        "value": "myindex-{{meta.language}}"
      }
    }
  ]
}
```

In FS crawler settings, set both `fs.lang_detect` and `elasticsearch.pipeline` options:

```json
{
  "name" : "test",
  "fs" : {
    "lang_detect" : true
  },
  "elasticsearch" : {
    "pipeline" : "langdetect"
  }
}
```

And then, a document containing french text will be sent to `myindex-fr`.
A document containing english text will be sent to `myindex-en`.

You can also imagine changing the field name from `content` to `content-fr` or `content-en`. That will help you
to define the correct analyzer to use.

Language detection might detect more than one language in a given text but only the most accurate will be set.
Which means that if you have a document containing 80% of french and 20% of english, the document will be marked
as `fr`.

Note that language detection is CPU and time consuming.

#### Storing binary source document (BASE64 encoded)

You can store in elasticsearch itself the binary document using `store_source` option:

```json
{
  "name" : "test",
  "fs" : {
    "store_source" : true
  }
}
```

In that case, a new field named `attachment` is added to the generated JSon document. This field is not indexed.
Default mapping for `attachment` field is:

```json
{
  "doc" : {
    "properties" : {
      "attachment" : {
        "type" : "binary",
        "doc_values" : false
      }
      // ... Other properties here
    }
  }
}
```

#### Extracted characters

By default FS crawler will extract only the first 100 000 characters.
But, you can set `indexed_chars` to `5000` in FS crawler settings in order to overwrite this default settings.

```json
{
  "name": "test",
  "fs": {
    "indexed_chars": "5000"
  }
}
```

This number can be either a fixed size, number of characters that is, or a percent using `%` sign.
The percentage value will be applied to the filesize to determine the number of character the crawler needs
to extract.

If you want to index only `80%` of filesize, define `indexed_chars` to `"80%"`.
Of course, if you want to index the full document, you can set this property to `"100%"`. Double values are also
supported so `"0.01%"` is also a correct value.

**Compressed files**: If your file is compressed, you might need to increase `indexed_chars` to more than `"100%"`.
For example, `"150%"`.

If you want to extract the full content, define `indexed_chars` to `"-1"`.

**Note**: Tika requires to allocate in memory a data structure to extract text. Setting `indexed_chars` to a high
number will require more memory!

#### File checksum

If you want FS crawler to generate a checksum for each file, set `checksum` to the algorithm you wish to use
to compute the checksum, such as `MD5` or `SHA-1`.

```json
{
  "name": "test",
  "fs": {
    "checksum": "MD5"
  }
}
```

#### Custom Tika Parsers

It might occur that one or more existing Tika parsers do not provide the intended information, or just do not exist.
This setting allows to use a custom parser instead.
The parsers must be provided as a .jar, but does not need to be on any classpath.
Note that this is an array. Here an example for just one:

```json
{
  "name": "test",
  "fs": {
    "custom_tika_parsers": [
      {
        "class_name": "org.me.MyParser",
        "path_to_jar": "/some/full/path/to/myParser-0.0.1-SNAPSHOT.jar",
        "mime_types": ["application/dns", "or-another-mimetype-from-tika"]
      }
    ]
  }
}
```

Some info about creating a custom parser is available [here](https://tika.apache.org/1.17/parser_guide.html)
Or use a existing parser as a blueprint. Make sure to choose the correct branch.
At the time of this writing fscrawler uses Tika 1.17, while on github the main Tika branch is 2.x.
The parsers from ["branch_1x"](https://github.com/apache/tika/tree/branch_1x/tika-parsers/src/main/java/org/apache/tika/parser) should work fine.

To build the custom parser separately, a pom file can be derived from the tika-parsers [pom.xml](https://github.com/apache/tika/blob/branch_1x/tika-parsers/pom.xml).
Probably a lot can be left out. Here is an example which requires fontbox.
(The exclusions are copied 1:1 from fscrawler's pom.xml, to be on the safe side)

<details><summary>Example pom.xml</summary>
<p>


```
<project xmlns="http://maven.apache.org/POM/4.0.0">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.me</groupId>
    <artifactId>myParser</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <tika.version>1.17</tika.version>
        <fontbox.version>2.0.8</fontbox.version>
    </properties>

    <build>
        <sourceDirectory>src</sourceDirectory>
        <!--<testSourceDirectory>test</testSourceDirectory>-->
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-javadoc-plugin</artifactId>
                <version>3.0.0-M1</version>
                <configuration>
                    <doclint>all,-missing,-accessibility</doclint>
                    <quiet>true</quiet>
                </configuration>
            </plugin>
        </plugins>
    </build>

    <dependencies>
        <dependency>
            <groupId>org.apache.tika</groupId>
            <artifactId>tika-parsers</artifactId>
            <version>${tika.version}</version>
            <exclusions>
                <!-- Not Apache2 License compatible -->
                <exclusion>
                    <groupId>edu.ucar</groupId>
                    <artifactId>netcdf</artifactId>
                </exclusion>
                <!-- Not Apache2 License compatible -->
                <exclusion>
                    <groupId>edu.ucar</groupId>
                    <artifactId>cdm</artifactId>
                </exclusion>
                <!-- Not Apache2 License compatible -->
                <exclusion>
                    <groupId>edu.ucar</groupId>
                    <artifactId>httpservices</artifactId>
                </exclusion>
                <!-- Not Apache2 License compatible -->
                <exclusion>
                    <groupId>edu.ucar</groupId>
                    <artifactId>grib</artifactId>
                </exclusion>
                <!-- Not Apache2 License compatible -->
                <exclusion>
                    <groupId>edu.ucar</groupId>
                    <artifactId>netcdf4</artifactId>
                </exclusion>
                <!-- Not Apache2 License compatible -->
                <exclusion>
                    <groupId>com.uwyn</groupId>
                    <artifactId>jhighlight</artifactId>
                </exclusion>
                <!-- ES core already has these -->
                <exclusion>
                    <groupId>org.ow2.asm</groupId>
                    <artifactId>asm-debug-all</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>commons-logging</groupId>
                    <artifactId>commons-logging-api</artifactId>
                </exclusion>
                <!-- Must be removed because it conflicts with Jersey (another JaxRS implementation) -->
                <exclusion>
                    <groupId>org.apache.cxf</groupId>
                    <artifactId>cxf-rt-rs-client</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.apache.pdfbox</groupId>
            <artifactId>fontbox</artifactId>
            <version>${fontbox.version}</version>
            <exclusions>
                <!-- Not Apache2 License compatible -->
                <exclusion>
                    <groupId>edu.ucar</groupId>
                    <artifactId>netcdf</artifactId>
                </exclusion>
                <!-- Not Apache2 License compatible -->
                <exclusion>
                    <groupId>edu.ucar</groupId>
                    <artifactId>cdm</artifactId>
                </exclusion>
                <!-- Not Apache2 License compatible -->
                <exclusion>
                    <groupId>edu.ucar</groupId>
                    <artifactId>httpservices</artifactId>
                </exclusion>
                <!-- Not Apache2 License compatible -->
                <exclusion>
                    <groupId>edu.ucar</groupId>
                    <artifactId>grib</artifactId>
                </exclusion>
                <!-- Not Apache2 License compatible -->
                <exclusion>
                    <groupId>edu.ucar</groupId>
                    <artifactId>netcdf4</artifactId>
                </exclusion>
                <!-- Not Apache2 License compatible -->
                <exclusion>
                    <groupId>com.uwyn</groupId>
                    <artifactId>jhighlight</artifactId>
                </exclusion>
                <!-- ES core already has these -->
                <exclusion>
                    <groupId>org.ow2.asm</groupId>
                    <artifactId>asm-debug-all</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>commons-logging</groupId>
                    <artifactId>commons-logging-api</artifactId>
                </exclusion>
                <!-- Must be removed because it conflicts with Jersey (another JaxRS implementation) -->
                <exclusion>
                    <groupId>org.apache.cxf</groupId>
                    <artifactId>cxf-rt-rs-client</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
    </dependencies>
</project>
```

</p>
</details>

### SSH settings

You can index files remotely using SSH.

Here is a list of SSH settings (under `server.` prefix)`:

|               Name               | Default value |                     Documentation                        |
|----------------------------------|---------------|----------------------------------------------------------|
| `server.hostname`                | `null`        | Hostname                                                 |
| `server.port`                    | `22`          | Port                                                     |
| `server.username`                | `null`        | [Username](#username--password)                          |
| `server.password`                | `null`        | [Password](#username--password)                          |
| `server.protocol`                | `"local"`     | Set it to `ssh`                                          |
| `server.pem_path`                | `null`        | [Using Username / PEM file](#using-username--pem-file)   |

#### Username / Password

Let's say you want to index from a remote server using SSH:

* FS URL: `/path/to/data/dir/on/server`
* Server: `mynode.mydomain.com`
* Username: `username`
* Password: `password`
* Protocol: `ssh` (default to `local`)
* Port: `22` (default to `22`)


```json
{
  "name" : "test",
  "fs" : {
    "url" : "/path/to/data/dir/on/server"
  },
  "server" : {
    "hostname" : "mynode.mydomain.com",
    "port" : 22,
    "username" : "username",
    "password" : "password",
    "protocol" : "ssh"
  }
}
```

#### Using Username / PEM file

Let's say you want to index from a remote server using SSH:

* FS URL: `/path/to/data/dir/on/server`
* Server: `mynode.mydomain.com`
* Username: `username`
* PEM File: `/path/to/private_key.pem`
* Protocol: `ssh` (default to `local`)
* Port: `22` (default to `22`)

```json
{
  "name" : "test",
  "fs" : {
    "url" : "/path/to/data/dir/on/server"
  },
  "server" : {
    "hostname" : "mynode.mydomain.com",
    "port" : 22,
    "username" : "username",
    "protocol" : "ssh",
	"pem_path": "/path/to/private_key.pem"
  }
}
```


### Elasticsearch settings

Here is a list of Elasticsearch settings (under `elasticsearch.` prefix)`:

|               Name               |    Default value     |                                 Documentation                                     |
|----------------------------------|----------------------|-----------------------------------------------------------------------------------|
| `elasticsearch.index`            | job name             | Index name for docs. See [Index settings](#index-settings)                        |
| `elasticsearch.index_folder`     | job name + _folder   | Index name for folders. See [Index settings](#index-settings)                     |
| `elasticsearch.bulk_size`        | `100`                | [Bulk settings](#bulk-settings)                                                   |
| `elasticsearch.flush_interval`   | `"5s"`               | [Bulk settings](#bulk-settings)                                                   |
| `elasticsearch.pipeline`         | `null`               | [Using Ingest Node Pipeline](#using-ingest-node-pipeline) (from 2.2)              |
| `elasticsearch.nodes`            |http://127.0.0.1:9200 | [Node settings](#node-settings)                                                   |
| `elasticsearch.username`         | `null`               | Username. See [Using credentials (X-Pack)](#using-credentials-x-pack) (from 2.2)  |
| `elasticsearch.password`         | `null`               | Password. See [Using credentials (X-Pack)](#using-credentials-x-pack) (from 2.2)  |

#### Index settings

By default, FS crawler will index your data in an index which name is the same as the crawler name (`name` property)
plus `_doc` suffix, like `test_doc`. You can change it by setting `index` field:

```json
{
  "name" : "test",
  "elasticsearch" : {
    "index" : "docs"
  }
}
```

When FS crawler needs to create the doc index, it applies some default settings and mappings which are read from
`~/.fscrawler/_default/5/_settings.json`.
You can read its content from [the source](src/main/resources/fr/pilato/elasticsearch/crawler/fs/_default/5/_settings.json).

Settings define an analyzer named `fscrawler_path` which uses a
[path hierarchy tokenizer](https://www.elastic.co/guide/en/elasticsearch/reference/current/analysis-pathhierarchy-tokenizer.html).

FS crawler will also index folders in an index which name is the same as the crawler name (`name` property)
plus `_folder` suffix, like `test_folder`. You can change it by setting `index_folder` field:

```json
{
 "name" : "test",
 "elasticsearch" : {
   "index_folder" : "folders"
 }
}
```

FS crawler applies as well a mapping automatically which is read from `~/.fscrawler/_default/5/_settings_folder.json`.
[Source here](src/main/resources/fr/pilato/elasticsearch/crawler/fs/_default/5/_settings_folder.json).

You can also display the index mapping being used with Kibana:

```
GET docs/_mapping
```

Or fall back to the command line:

```sh
curl 'http://localhost:9200/docs/_mapping?pretty'
```

##### Creating your own mapping (analyzers)

If you want to define your own index settings and mapping to set analyzers for example, you can either create the index
and push the mapping or define a
`~/.fscrawler/_default/5/_settings.json` document which contains the index settings and mappings you wish
**before starting the FS crawler**.

The following example uses a `french` analyzer to index the `content` field.

```json
{
  "settings": {
    "index.mapping.total_fields.limit": 2000,
    "analysis": {
      "analyzer": {
        "fscrawler_path": {
          "tokenizer": "fscrawler_path"
        }
      },
      "tokenizer": {
        "fscrawler_path": {
          "type": "path_hierarchy"
        }
      }
    }
  },
  "mappings": {
    "doc": {
      "properties" : {
        "attachment" : {
          "type" : "binary",
          "doc_values": false
        },
        "attributes" : {
          "properties" : {
            "group" : {
              "type" : "keyword"
            },
            "owner" : {
              "type" : "keyword"
            }
          }
        },
        "content" : {
          "type" : "text",
          "analyzer" : "french"
        },
        "file" : {
          "properties" : {
            "content_type" : {
              "type" : "keyword"
            },
            "filename" : {
              "type" : "keyword"
            },
            "extension" : {
              "type" : "keyword"
            },
            "filesize" : {
              "type" : "long"
            },
            "indexed_chars" : {
              "type" : "long"
            },
            "indexing_date" : {
              "type" : "date",
              "format" : "dateOptionalTime"
            },
            "last_modified" : {
              "type" : "date",
              "format" : "dateOptionalTime"
            },
            "checksum": {
              "type": "keyword"
            },
            "url" : {
              "type" : "keyword",
              "index" : false
            }
          }
        },
        "object" : {
          "type" : "object"
        },
        "meta" : {
          "properties" : {
            "author" : {
              "type" : "text"
            },
            "date" : {
              "type" : "date",
              "format" : "dateOptionalTime"
            },
            "keywords" : {
              "type" : "text"
            },
            "title" : {
              "type" : "text"
            },
            "language" : {
              "type" : "keyword"
            },
            "format" : {
              "type" : "text"
            },
            "identifier" : {
              "type" : "text"
            },
            "contributor" : {
              "type" : "text"
            },
            "coverage" : {
              "type" : "text"
            },
            "modifier" : {
              "type" : "text"
            },
            "creator_tool" : {
              "type" : "keyword"
            },
            "publisher" : {
              "type" : "text"
            },
            "relation" : {
              "type" : "text"
            },
            "rights" : {
              "type" : "text"
            },
            "source" : {
              "type" : "text"
            },
            "type" : {
              "type" : "text"
            },
            "description" : {
              "type" : "text"
            },
            "created" : {
              "type" : "date",
              "format" : "dateOptionalTime"
            },
            "print_date" : {
              "type" : "date",
              "format" : "dateOptionalTime"
            },
            "metadata_date" : {
              "type" : "date",
              "format" : "dateOptionalTime"
            },
            "latitude" : {
              "type" : "text"
            },
            "longitude" : {
              "type" : "text"
            },
            "altitude" : {
              "type" : "text"
            },
            "rating" : {
              "type" : "keyword"
            },
            "comments" : {
              "type" : "text"
            }
          }
        },
        "path" : {
          "properties" : {
            "real" : {
              "type" : "keyword",
              "fields": {
                "tree": {
                  "type" : "text",
                  "analyzer": "fscrawler_path",
                  "fielddata": true
                }
              }
            },
            "root" : {
              "type" : "keyword"
            },
            "virtual" : {
              "type" : "keyword",
              "fields": {
                "tree": {
                  "type" : "text",
                  "analyzer": "fscrawler_path",
                  "fielddata": true
                }
              }
            }
          }
        }
      }
    }
  }
}
```

Note that if you want to push manually the mapping to elasticsearch you can use the classic REST calls:

```
# Create index (don't forget to add the fscrawler_path analyzer)
PUT docs
{
  // Same index settings as previously seen
}
```

##### Define explicit mapping/settings per job

Let's say you created a job named `job_name` and you are sending documents against an elasticsearch cluster
running version `5.x`.

If you create the following files, they will be picked up at job start time instead of the
[default ones](#autogenerated-mapping):

* `~/.fscrawler/{job_name}/_mappings/5/_settings.json`
* `~/.fscrawler/{job_name}/_mappings/5/_settings_folder.json`

You can do the same for other elasticsearch versions with:

* `~/.fscrawler/{job_name}/_mappings/1/_settings.json` for 1.x series
* `~/.fscrawler/{job_name}/_mappings/1/_settings_folder.json` for 1.x series
* `~/.fscrawler/{job_name}/_mappings/2/_settings.json` for 2.x series
* `~/.fscrawler/{job_name}/_mappings/2/_settings_folder.json` for 2.x series
* `~/.fscrawler/{job_name}/_mappings/6/_settings.json` for 6.x series
* `~/.fscrawler/{job_name}/_mappings/6/_settings_folder.json` for 6.x series


##### Replace existing mapping

Unfortunately you can not change the mapping on existing data.
Therefore, you'll need first to remove existing index, which means remove all existing data, and then restart FS crawler
with the new mapping.

You might to try [elasticsearch Reindex API](https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-reindex.html) though.


#### Bulk settings

FS crawler is using bulks to send data to elasticsearch. By default the bulk is executed every 100 operations or 
every 5 seconds. You can change default settings using `bulk_size` and `flush_interval`:

```json
{
  "name" : "test",
  "elasticsearch" : {
    "bulk_size" : 1000,
    "flush_interval" : "2s"
  }
}
```

Note that elasticsearch has a [default limit of `100mb` per HTTP request](https://www.elastic.co/guide/en/elasticsearch/reference/current/modules-http.html).
Which means that if you are indexing a massive bulk of documents, you might hit that limit and FSCrawler will throw
an error like `entity content is too long [xxx] for the configured buffer limit [104857600]`.

You can either change this limit on elasticsearch side by setting `http.max_content_length` to a higher value but please
be aware that this will consume much more memory on elasticsearch side.

Or you can decrease the `bulk_size` setting to a smaller value.

#### Using Ingest Node Pipeline

If you are using an elasticsearch cluster running a 5.0 or superior version, you
can use an Ingest Node pipeline to transform documents sent by FS crawler before they are actually indexed.
Please note that folder objects are not sent through the pipeline as they are more internal objects.

For example, if you have the following pipeline:

```sh
PUT _ingest/pipeline/fscrawler
{
  "description" : "fscrawler pipeline",
  "processors" : [
    {
      "set" : {
        "field": "foo",
        "value": "bar"
      }
    }
  ]
}
```

In FS crawler settings, set the `elasticsearch.pipeline` option:

```json
{
  "name" : "test",
  "elasticsearch" : {
    "pipeline" : "fscrawler"
  }
}
```

Note that this option is available from FS crawler 2.2.

#### Node settings

FS crawler is using elasticsearch REST layer to send data to your running cluster.
By default, it connects to `127.0.0.1` on port `9200` which are the default settings when
running a local node on your machine.

Of course, in production, you would probably change this and connect to a production cluster:

```json
{
  "name" : "test",
  "elasticsearch" : {
    "nodes" : [
      { "host" : "mynode1.mycompany.com", "port" : 9200, "scheme" : "HTTP" }
    ]
  }
}
```

You can define multiple nodes:

```json
{
  "name" : "test",
  "elasticsearch" : {
    "nodes" : [
      { "host" : "mynode1.mycompany.com", "port" : 9200, "scheme" : "HTTP" },
      { "host" : "mynode2.mycompany.com", "port" : 9200, "scheme" : "HTTP" },
      { "host" : "mynode3.mycompany.com", "port" : 9200, "scheme" : "HTTP" }
    ]
  }
}
```

You can use HTTPS instead of default HTTP (from 2.2):

```json
{
  "name" : "test",
  "elasticsearch" : {
    "nodes" : [
      { "host" : "CLUSTERID.eu-west-1.aws.found.io", "port" : 9243, "scheme" : "HTTPS" }
    ]
  }
}
```


#### Using Credentials (X-Pack)

If you secured your elasticsearch cluster with [X-Pack](https://www.elastic.co/downloads/x-pack), you can provide
`username` and `password` to FS crawler:

```json
{
  "name" : "test",
  "elasticsearch" : {
    "username" : "elastic",
    "password" : "changeme"
  }
}
```

**WARNING**: note that for the current version, the elasticsearch password is stored in plain text in
your job setting file.

A better practice is to only set the username or pass it with `--username elastic` option when starting
FS Crawler.

If the password is not defined, you will be prompted when starting the job:

```
22:46:42,528 INFO  [f.p.e.c.f.FsCrawler] Password for elastic:
```

#### Generated fields

FS crawler creates the following fields :

|         Field        |                Description                  |                    Example                  |                                                          Javadoc                                               |
|----------------------|---------------------------------------------|---------------------------------------------|----------------------------------------------------------------------------------------------------------------|
| `content`            | Extracted content                           | `"This is my text!"`                        |                                                                                                                |
| `attachment`         | BASE64 encoded binary file                  | BASE64 Encoded document                     |                                                                                                                |
| `meta.author`        | Author if any in document metadata          | `"David Pilato"`                            |[CREATOR](https://tika.apache.org/1.17/api/org/apache/tika/metadata/TikaCoreProperties.html#CREATOR)            |
| `meta.title`         | Title if any in document metadata           | `"My document title"`                       |[TITLE](https://tika.apache.org/1.17/api/org/apache/tika/metadata/TikaCoreProperties.html#TITLE)                |
| `meta.date`          | Last modified date                          | `"2013-04-04T15:21:35"`                     |[MODIFIED](https://tika.apache.org/1.17/api/org/apache/tika/metadata/TikaCoreProperties.html#MODIFIED)          |
| `meta.keywords`      | Keywords if any in document metadata        | `["river","fs","elasticsearch"]`            |[KEYWORDS](https://tika.apache.org/1.17/api/org/apache/tika/metadata/TikaCoreProperties.html#KEYWORDS)          |
| `meta.language`      | Language (can be detected)                  | `"fr"`                                      |[LANGUAGE](https://tika.apache.org/1.17/api/org/apache/tika/metadata/TikaCoreProperties.html#LANGUAGE)          |
| `meta.format`        | Format of the media                         | `"application/pdf; version=1.6"`            |[FORMAT](https://tika.apache.org/1.17/api/org/apache/tika/metadata/TikaCoreProperties.html#FORMAT)              |
| `meta.identifier`    | URL/DOI/ISBN for example                    | `"FOOBAR"`                                  |[IDENTIFIER](https://tika.apache.org/1.17/api/org/apache/tika/metadata/TikaCoreProperties.html#IDENTIFIER)      |
| `meta.contributor`   | Contributor                                 | `"foo bar"`                                 |[CONTRIBUTOR](https://tika.apache.org/1.17/api/org/apache/tika/metadata/TikaCoreProperties.html#CONTRIBUTOR)    |
| `meta.coverage`      | Coverage                                    | `"FOOBAR"`                                  |[COVERAGE](https://tika.apache.org/1.17/api/org/apache/tika/metadata/TikaCoreProperties.html#COVERAGE)          |
| `meta.modifier`      | Last author                                 | `"David Pilato"`                            |[MODIFIER](https://tika.apache.org/1.17/api/org/apache/tika/metadata/TikaCoreProperties.html#MODIFIER)          |
| `meta.creator_tool`  | Tool used to create the resource            | `"HTML2PDF - TCPDF"`                        |[CREATOR_TOOL](https://tika.apache.org/1.17/api/org/apache/tika/metadata/TikaCoreProperties.html#CREATOR_TOOL)  |
| `meta.publisher`     | Publisher: person, organisation, service    | `"elastic"`                                 |[PUBLISHER](https://tika.apache.org/1.17/api/org/apache/tika/metadata/TikaCoreProperties.html#PUBLISHER)        |
| `meta.relation`      | Related resource                            | `"FOOBAR"`                                  |[RELATION](https://tika.apache.org/1.17/api/org/apache/tika/metadata/TikaCoreProperties.html#RELATION)          |
| `meta.rights`        | Information about rights                    | `"CC-BY-ND"`                                |[RIGHTS](https://tika.apache.org/1.17/api/org/apache/tika/metadata/TikaCoreProperties.html#RIGHTS)              |
| `meta.source`        | Source for the current document (derivated) | `"FOOBAR"`                                  |[SOURCE](https://tika.apache.org/1.17/api/org/apache/tika/metadata/TikaCoreProperties.html#SOURCE)              |
| `meta.type`          | Nature or genre of the content              | `"Image"`                                   |[TYPE](https://tika.apache.org/1.17/api/org/apache/tika/metadata/TikaCoreProperties.html#TYPE)                  |
| `meta.description`   | An account of the content                   | `"This is a description"`                   |[DESCRIPTION](https://tika.apache.org/1.17/api/org/apache/tika/metadata/TikaCoreProperties.html#DESCRIPTION)    |
| `meta.created`       | Date of creation                            | `"2013-04-04T15:21:35"`                     |[CREATED](https://tika.apache.org/1.17/api/org/apache/tika/metadata/TikaCoreProperties.html#CREATED)            |
| `meta.print_date`    | When was the document last printed?         | `"2013-04-04T15:21:35"`                     |[PRINT_DATE](https://tika.apache.org/1.17/api/org/apache/tika/metadata/TikaCoreProperties.html#PRINT_DATE)      |
| `meta.metadata_date` | Last modification of metadata               | `"2013-04-04T15:21:35"`                     |[METADATA_DATE](https://tika.apache.org/1.17/api/org/apache/tika/metadata/TikaCoreProperties.html#METADATA_DATE)|
| `meta.latitude`      | The WGS84 Latitude of the Point             | `"N 48° 51' 45.81''"`                       |[LATITUDE](https://tika.apache.org/1.17/api/org/apache/tika/metadata/TikaCoreProperties.html#LATITUDE)          |
| `meta.longitude`     | The WGS84 Longitude of the Point            | `"E 2° 17' 15.331''"`                       |[LONGITUDE](https://tika.apache.org/1.17/api/org/apache/tika/metadata/TikaCoreProperties.html#LONGITUDE)        |
| `meta.altitude`      | The WGS84 Altitude of the Point             | `""`                                        |[ALTITUDE](https://tika.apache.org/1.17/api/org/apache/tika/metadata/TikaCoreProperties.html#ALTITUDE)          |
| `meta.rating`        | A user-assigned rating -1, [0..5]           | `0`                                         |[RATING](https://tika.apache.org/1.17/api/org/apache/tika/metadata/TikaCoreProperties.html#RATING)              |
| `meta.comments`      | Comments                                    | `"Comments"`                                |[COMMENTS](https://tika.apache.org/1.17/api/org/apache/tika/metadata/TikaCoreProperties.html#COMMENTS)          |
| `meta.raw`           | An object with all raw metadata             | `"meta.raw.channels" : "2"`                 |                                                                                                                |
| `file.content_type`  | Content Type                                | `"application/vnd.oasis.opendocument.text"` |                                                                                                                |
| `file.last_modified` | Last modification date                      | `1386855978000`                             |                                                                                                                |
| `file.indexing_date` | Indexing date                               | `"2013-12-12T13:50:58.758Z"`                |                                                                                                                |
| `file.filesize`      | File size in bytes                          | `1256362`                                   |                                                                                                                |
| `file.indexed_chars` | Extracted chars if `fs.indexed_chars` > 0   | `100000`                                    |                                                                                                                |
| `file.filename`      | Original file name                          | `"mydocument.pdf"`                          |                                                                                                                |
| `file.extension`     | Original file name extension (from 2.2)     | `"pdf"`                                     |                                                                                                                |
| `file.url`           | Original file url                           | `"file://tmp/mydir/otherdir/mydocument.pdf"`|                                                                                                                |
| `file.checksum`      | Checksum if `fs.checksum` set               | `"c32eafae2587bef4b3b32f73743c3c61"`        |                                                                                                                |
| `path.virtual`       | Relative path from root path                | `"/mydir/otherdir/mydocument.pdf"`          |                                                                                                                |
| `path.root`          | MD5 encoded parent path (for internal use)  | `"112aed83738239dbfe4485f024cd4ce1"`        |                                                                                                                |
| `path.real`          | Actual real path name                       | `"/tmp/mydir/otherdir/mydocument.pdf"`      |                                                                                                                |
| `attributes.owner`   | Owner name                                  | `"david"`                                   |                                                                                                                |
| `attributes.group`   | Group name                                  | `"staff"`                                   |                                                                                                                |

For more information about meta data, please read the [TikaCoreProperties javadoc](https://tika.apache.org/1.17/api/org/apache/tika/metadata/TikaCoreProperties.html).

Here is a typical JSON document generated by the crawler:

```json
{
   "file":{
      "filename":"test.odt",
      "extension":"odt",
      "last_modified":1386855978000,
      "indexing_date":"2013-12-12T13:50:58.758Z",
      "content_type":"application/vnd.oasis.opendocument.text",
      "url":"file:///tmp/testfs_metadata/test.odt",
      "indexed_chars":100000,
      "filesize":8355,
      "checksum":"c32eafae2587bef4b3b32f73743c3c61"
   },
   "path":{
      "root":"bceb3913f6d793e915beb70a4735592",
      "virtual":"/test.odt",
      "real":"/tmp/testfs_metadata/test.odt"
   },
   "attributes": {
      "owner": "david",
      "group": "staff"
   },
   "meta":{
      "author":"David Pilato",
      "title":"Mon titre",
      "date":"2013-04-04T15:21:35",
      "keywords":[
         "fs",
         "elasticsearch",
         "crawler"
      ],
      "language":"fr"
   },
   "content":"Bonjour David\n\n\n"
}
```

#### Search examples

You can use the content field to perform full-text search on

```
GET docs/_search
{
  "query" : {
    "match" : {
        "content" : "the quick brown fox"
    }
  }
}
```

You can use meta fields to perform search on.

```
GET docs/_search
{
  "query" : {
    "term" : {
        "file.filename" : "mydocument.pdf"
    }
  }
}
```

Or run some aggregations on top of them, like:

```
GET docs/_search
{
  "size": 0,
  "aggs": {
    "by_extension": {
      "terms": {
        "field": "file.extension"
      }
    }
  }
}
```

### REST service

From 2.2, FS crawler comes with a REST service available by default at `http://127.0.0.1:8080/fscrawler`.
To activate it, launch FS Crawler with `--rest` option.

#### FS Crawler status

To get an overview of the running service, you can call `GET /` endpoint:

```sh
curl http://127.0.0.1:8080/fscrawler/
```

It will give you a response similar to:

```json
{
  "ok" : true,
  "version" : "2.2",
  "elasticsearch" : "5.1.1",
  "settings" : {
    "name" : "fscrawler-rest-tests",
    "fs" : {
      "url" : "/tmp/es",
      "update_rate" : "15m",
      "json_support" : false,
      "filename_as_id" : false,
      "add_filesize" : true,
      "remove_deleted" : true,
      "store_source" : false,
      "index_content" : true,
      "attributes_support" : false,
      "raw_metadata" : true,
      "xml_support" : false,
      "index_folders" : true,
      "lang_detect" : false
    },
    "elasticsearch" : {
      "nodes" : [ {
        "host" : "127.0.0.1",
        "port" : 9200,
        "scheme" : "HTTP"
      } ],
      "index" : "fscrawler-rest-tests_doc",
      "index_folder" : "fscrawler-rest-tests_folder",
      "bulk_size" : 100,
      "flush_interval" : "5s",
      "username" : "elastic"
    },
    "rest" : {
      "scheme" : "HTTP",
      "host" : "127.0.0.1",
      "port" : 8080,
      "endpoint" : "fscrawler"
    }
  }
}
```

#### Uploading a binary document

To upload a binary, you can call `POST /_upload` endpoint:

```sh
echo "This is my text" > test.txt
curl -F "file=@test.txt" "http://127.0.0.1:8080/fscrawler/_upload"
```

It will give you a response similar to:

```json
{
  "ok" : true,
  "filename" : "test.txt",
  "url" : "http://127.0.0.1:9200/fscrawler-rest-tests_doc/doc/dd18bf3a8ea2a3e53e2661c7fb53534"
}
```

The `url` represents the elasticsearch address of the indexed document.
If you call:

```sh
curl http://127.0.0.1:9200/fscrawler-rest-tests_doc/doc/dd18bf3a8ea2a3e53e2661c7fb53534?pretty
```

You will get back your document as it has been stored by elasticsearch:

```json
{
  "_index" : "fscrawler-rest-tests_doc",
  "_type" : "doc",
  "_id" : "dd18bf3a8ea2a3e53e2661c7fb53534",
  "_version" : 1,
  "found" : true,
  "_source" : {
    "content" : "This file contains some words.\n",
    "meta" : {
      "raw" : {
        "X-Parsed-By" : "org.apache.tika.parser.DefaultParser",
        "Content-Encoding" : "ISO-8859-1",
        "Content-Type" : "text/plain; charset=ISO-8859-1"
      }
    },
    "file" : {
      "extension" : "txt",
      "content_type" : "text/plain; charset=ISO-8859-1",
      "indexing_date" : "2017-01-04T21:01:08.043",
      "filename" : "test.txt"
    },
    "path" : {
      "virtual" : "test.txt",
      "real" : "test.txt"
    }
  }
}
```


If you started FS crawler in debug mode with `--debug` or if you pass `debug=true` query parameter,
then the response will be much more complete:

```sh
echo "This is my text" > test.txt
curl -F "file=@test.txt" "http://127.0.0.1:8080/fscrawler/_upload?debug=true"
```

will give

```json
{
  "ok" : true,
  "filename" : "test.txt",
  "url" : "http://127.0.0.1:9200/fscrawler-rest-tests_doc/doc/dd18bf3a8ea2a3e53e2661c7fb53534",
  "doc" : {
    "content" : "This file contains some words.\n",
    "meta" : {
      "raw" : {
        "X-Parsed-By" : "org.apache.tika.parser.DefaultParser",
        "Content-Encoding" : "ISO-8859-1",
        "Content-Type" : "text/plain; charset=ISO-8859-1"
      }
    },
    "file" : {
      "extension" : "txt",
      "content_type" : "text/plain; charset=ISO-8859-1",
      "indexing_date" : "2017-01-04T14:05:10.325",
      "filename" : "test.txt"
    },
    "path" : {
      "virtual" : "test.txt",
      "real" : "test.txt"
    }
  }
}
```

#### Simulate Upload

If you want to get back the extracted content and its metadata but without indexing into elasticsearch
you can use `simulate=true` query parameter:

```sh
echo "This is my text" > test.txt
curl -F "file=@test.txt" "http://127.0.0.1:8080/fscrawler/_upload?debug=true&simulate=true"
```

#### Document ID

By default, FS crawler encodes the filename to generate an id. Which means that if you send 2 files
with the same filename `test.txt`, the second one will overwrite the first one because they will both share
the same ID.

You can force any id you wish by adding `id=YOUR_ID` in the form data:

```sh
echo "This is my text" > test.txt
curl -F "file=@test.txt" -F "id=my-test" "http://127.0.0.1:8080/fscrawler/_upload"
```

There is a specific id named `_auto_` where the ID will be autogenerated by elasticsearch.
It means that sending twice the same file will result in 2 different documents indexed.

#### REST settings

Here is a list of REST service settings (under `rest.` prefix)`:

|       Name       | Default value |             Documentation               |
|------------------|---------------|-----------------------------------------|
| `rest.scheme`    | `http`        | Scheme. Can be either `http` or `https` |
| `rest.host`      | `127.0.0.1`   | Bound host                              |
| `rest.port`      | `8080`        | Bound port                              |
| `rest.endpoint`  | `fscrawler`   | Endpoint                                |

Most [local FS settings](#local-fs-settings) (under `fs` in the settings file) also affect the REST service, e.g. `fs.indexed_chars`.
Local FS settings that do *not* affect the REST service are those such as `url`, `update_rate`, `includes`, `excludes`.
Please check [Local FS settings](#local-fs-settings) for more details.


REST service is running at `http://127.0.0.1:8080/fscrawler` by default.

You can change it using `rest` settings:

```json
{
  "name" : "test",
  "rest" : {
    "scheme" : "HTTP",
    "host" : "192.168.0.1",
    "port" : 8180,
    "endpoint" : "my_fscrawler"
  }
}
```

It also means that if you are running more than one instance of FS crawler locally, you can (must) change
the `port`.

# Tips and tricks

## Moving files to a "watched" directory

When moving an existing file to the directory FSCrawler is watching, you need to explicitly `touch` all the files
as when moved, the files are keeping their original date intact:

```sh
# single file
touch file_you_moved

# all files
find  -type f  -exec touch {} +

# all .txt files
find  -type f  -name "*.txt" -exec touch {} +
```

Or you need to [restart FSCrawler](#crawler-options) from the beginning with the `--restart` option which will
reindex everything.

## Indexing on HDFS

There is no specific support for HDFS in FS crawler. But you can [mount your HDFS on your machine](https://wiki.apache.org/hadoop/MountableHDFS)
and run FS crawler on this mount point. You can also read details about
[HDFS NFS Gateway](http://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-hdfs/HdfsNfsGateway.html).

## OCR integration

To deal with images containing text, just [install Tesseract](https://github.com/tesseract-ocr/tesseract/wiki). Tesseract will be auto-detected by Tika
or you can explicitly [set the path to tesseract binary](#ocr-path).
Then add an image (png, jpg, ...) into your Fscrawler [root directory](#root-directory). After the next index update, the text will be indexed and placed in "_source.content".

By default, FS crawler will try to extract also images from your PDF documents and run OCR on them.
This can be a CPU intensive operation. If you don't mean to run OCR on PDF but only on images, you can set `fs.pdf_ocr`
to `false`:

```json
{
  "name" : "test",
  "fs" : {
    "pdf_ocr" : false
  }
}
```

### OCR settings

Here is a list of OCR settings (under `fs.ocr` prefix)`:

|               Name               | Default value |                                 Documentation                                     |
|----------------------------------|---------------|-----------------------------------------------------------------------------------|
| `fs.ocr.language`                | `"eng"`       | [OCR Language](#ocr-language)                                                     |
| `fs.ocr.path`                    | `null`        | [OCR Path](#ocr-path)                                                             |
| `fs.ocr.data_path`               | `null`        | [OCR Data Path](#ocr-data-path)                                                             |

#### OCR Language

If you have installed a [Tesseract Language pack](https://wiki.apache.org/tika/TikaOCR), you can use it when
parsing your documents by setting `fs.ocr.language` property in your `~/.fscrawler/test/_settings.json` file:

```json
{
  "name" : "test",
  "fs" : {
    "url" : "/path/to/data/dir",
    "ocr" : {
      "language": "eng"
    }
  }
}
```

#### OCR Path

If your Tesseract application is not available in default system PATH, you can define the path to use
by setting `fs.ocr.path` property in your `~/.fscrawler/test/_settings.json` file:

```json
{
  "name" : "test",
  "fs" : {
    "url" : "/path/to/data/dir",
    "ocr" : {
      "path": "/path/to/tesseract/executable"
    }
  }
}
```

When you set it, it's highly recommended to [set the data path for Tesseract](#ocr-data-path).

#### OCR Data Path

Set the path to the 'tessdata' folder, which contains language files and config files if Tesseract
can not be automatically detected. You can define the path to use
by setting `fs.ocr.data_path` property in your `~/.fscrawler/test/_settings.json` file:

```json
{
  "name" : "test",
  "fs" : {
    "url" : "/path/to/data/dir",
    "ocr" : {
      "path": "/path/to/tesseract/executable",
      "data_path": "/path/to/tesseract/tessdata"
    }
  }
}
```

## Using docker

To use FS crawler with [docker](https://www.docker.com/), check
[docker-fscrawler](https://github.com/shadiakiki1986/docker-fscrawler) recipe.

# License

```
This software is licensed under the Apache 2 license, quoted below.

Copyright 2011-2017 David Pilato

Licensed under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License. You may obtain a copy of
the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
License for the specific language governing permissions and limitations under
the License.
```

# Incompatible 3rd party library licenses

Some libraries are not Apache2 compatible. Therefore they are not packaged with FSCrawler so you need
to download and add manually them to the `lib` directory:

* `jbig2`: [com.levigo.jbig2:levigo-jbig2-imageio:2.0](http://repo1.maven.org/maven2/com/levigo/jbig2/levigo-jbig2-imageio/)
* `tiff`: [com.github.jai-imageio:jai-imageio-core:1.3.1](http://repo1.maven.org/maven2/com/github/jai-imageio/jai-imageio-core/)
* `JPEG2000`: [com.github.jai-imageio:jai-imageio-jpeg2000:1.3.0](http://repo1.maven.org/maven2/com/github/jai-imageio/jai-imageio-jpeg2000/)

See [pdfbox](https://pdfbox.apache.org/2.0/dependencies.html#jai-image-io) for more details.

