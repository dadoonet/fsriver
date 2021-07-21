.. _local-fs-settings:

Local FS settings
-----------------

Here is a list of Local FS settings (under ``fs.`` prefix)`:

+----------------------------+-----------------------+---------------------------------+
| Name                       | Default value         | Documentation                   |
+============================+=======================+=================================+
| ``fs.url``                 | ``"/tmp/es"``         | `Root directory`_               |
+----------------------------+-----------------------+---------------------------------+
| ``fs.update_rate``         | ``"15m"``             | `Update Rate`_                  |
+----------------------------+-----------------------+---------------------------------+
| ``fs.includes``            | ``null``              | `Includes and excludes`_        |
+----------------------------+-----------------------+---------------------------------+
| ``fs.excludes``            | ``["*/~*"]``          | `Includes and excludes`_        |
+----------------------------+-----------------------+---------------------------------+
| ``fs.filters``             | ``null``              | `Filter content`_               |
+----------------------------+-----------------------+---------------------------------+
| ``fs.json_support``        | ``false``             | `Indexing JSon docs`_           |
+----------------------------+-----------------------+---------------------------------+
| ``fs.xml_support``         | ``false``             | `Indexing XML docs`_            |
+----------------------------+-----------------------+---------------------------------+
| ``fs.add_as_inner_object`` | ``false``             | `Add as Inner Object`_          |
+----------------------------+-----------------------+---------------------------------+
| ``fs.index_folders``       | ``true``              | `Index folders`_                |
+----------------------------+-----------------------+---------------------------------+
| ``fs.attributes_support``  | ``false``             | `Adding file attributes`_       |
+----------------------------+-----------------------+---------------------------------+
| ``fs.raw_metadata``        | ``false``             | `Disabling raw metadata`_       |
+----------------------------+-----------------------+---------------------------------+
| ``fs.filename_as_id``      | ``false``             | :ref:`filename-as-id`           |
+----------------------------+-----------------------+---------------------------------+
| ``fs.add_filesize``        | ``true``              | `Disabling file size field`_    |
+----------------------------+-----------------------+---------------------------------+
| ``fs.remove_deleted``      | ``true``              | `Ignore deleted files`_         |
+----------------------------+-----------------------+---------------------------------+
| ``fs.store_source``        | ``false``             | :ref:`store_binary`             |
+----------------------------+-----------------------+---------------------------------+
| ``fs.index_content``       | ``true``              | `Ignore content`_               |
+----------------------------+-----------------------+---------------------------------+
| ``fs.lang_detect``         | ``false``             | `Language detection`_           |
+----------------------------+-----------------------+---------------------------------+
| ``fs.continue_on_error``   | ``false``             | :ref:`continue_on_error`        |
+----------------------------+-----------------------+---------------------------------+
| ``fs.pdf_ocr``             | ``true``              | :ref:`ocr_integration`          |
+----------------------------+-----------------------+---------------------------------+
| ``fs.indexed_chars``       | ``100000.0``          | `Extracted characters`_         |
+----------------------------+-----------------------+---------------------------------+
| ``fs.ignore_above``        | ``null``              | `Ignore above`_                 |
+----------------------------+-----------------------+---------------------------------+
| ``fs.checksum``            | ``null``              | `File Checksum`_                |
+----------------------------+-----------------------+---------------------------------+
| ``fs.follow_symlinks``     | ``false``             | `Follow Symlinks`_              |
+----------------------------+-----------------------+---------------------------------+
| ``fs.skip_tika: true``     | ``false``             | `Skip tika parser`_             |
+----------------------------+-----------------------+---------------------------------+

.. _root-directory:

Root directory
^^^^^^^^^^^^^^

Define ``fs.url`` property in your ``~/.fscrawler/test/_settings.yaml``
file:

.. code:: yaml

   name: "test"
   fs:
     url: "/path/to/data/dir"

For Windows users, use a form like ``c:/tmp`` or ``c:\\tmp``.

.. _local-fs-update_rate:

Update rate
^^^^^^^^^^^

By default, ``update_rate`` is set to ``15m``. You can modify this value
using any compatible `time
unit <https://www.elastic.co/guide/en/elasticsearch/reference/current/common-options.html#time-units>`__.

For example, here is a 15 minutes update rate:

.. code:: yaml

   name: "test"
   fs:
     update_rate: "15m"

Or a 3 hours update rate:

.. code:: yaml

   name: "test"
   fs:
     update_rate: "3h"

``update_rate`` is the pause duration between the last time we read the
file system and another run. Which means that if you set it to ``15m``,
the next scan will happen on 15 minutes after the end of the current
scan, whatever its duration.

.. _includes_excludes:

Includes and excludes
^^^^^^^^^^^^^^^^^^^^^

Let’s say you want to index only docs like ``*.doc`` and ``*.pdf`` but
``resume*``. So ``resume_david.pdf`` won’t be indexed.

Define ``fs.includes`` and ``fs.excludes`` properties in your
``~/.fscrawler/test/_settings.yaml`` file:

.. code:: yaml

   name: "test"
   fs:
     includes:
     - "*/*.doc"
     - "*/*.pdf"
     excludes:
     - "*/resume*"

By default, FSCrawler will exclude files starting with ``~``.

.. versionadded:: 2.5

It also applies to directory names. So if you want to ignore ``.ignore``
dir, just add ``.ignore`` as an excluded name. Note that ``includes`` and ``excludes``
apply to directory names as well.

Let's take the following example with the ``root`` dir as ``/tmp``:

.. code::

    /tmp
    ├── folderA
    │   ├── subfolderA
    │   ├── subfolderB
    │   └── subfolderC
    ├── folderB
    │   ├── subfolderA
    │   ├── subfolderB
    │   └── subfolderC
    └── folderC
        ├── subfolderA
        ├── subfolderB
        └── subfolderC

If you define the following ``fs.excludes`` property in your
``~/.fscrawler/test/_settings.yaml`` file:

.. code:: yaml

   name: "test"
   fs:
     excludes:
     - "/folderB/subfolder*"

Then all files but the ones in ``/folderB/subfolderA``, ``/folderB/subfolderB`` and
``/folderB/subfolderC`` will be indexed.

Since the includes and excludes work on the entire *path of the file* you must consider that when using wildcards. Below are some includes and excludes pattern to help convey the idea better.

+--------------------+------------------------------------------------+------------------------------------------------+
| Pattern            | Includes                                       | Excludes                                       |
+====================+================================================+================================================+
| ``*.jpg``          | Include all jpg files                          | exclude all jpg files                          |
+--------------------+------------------------------------------------+------------------------------------------------+
| ``/images/*.jpg``  | Include all jpg files in the images directory  | Exclude all jpg files in the images directory  |
+--------------------+------------------------------------------------+------------------------------------------------+
| ``*/old-*.jpg``    | Include all jpg files that start with ``old-`` | Exclude all jpg files that start with ``old-`` |
+--------------------+------------------------------------------------+------------------------------------------------+

.. versionadded:: 2.6

If a folder contains a file named ``.fscrawlerignore``, this folder and its subfolders will be entirely skipped.

Filter content
^^^^^^^^^^^^^^

.. versionadded:: 2.5

You can filter out documents you would like to index by adding one or more
regular expression that match the extracted content.
Documents which are not matching will be simply ignored and not indexed.

If you define the following ``fs.filters`` property in your
``~/.fscrawler/test/_settings.yaml`` file:

.. code:: yaml

   name: "test"
   fs:
     filters:
     - ".*foo.*"
     - "^4\\d{3}([\\ \\-]?)\\d{4}\\1\\d{4}\\1\\d{4}$"

With this example, only documents which contains the word ``foo`` and a VISA credit card number
with the form like ``4012888888881881``, ``4012 8888 8888 1881`` or ``4012-8888-8888-1881``
will be indexed.


Indexing JSon docs
^^^^^^^^^^^^^^^^^^

If you want to index JSon files directly without parsing with Tika, you
can set ``json_support`` to ``true``. JSon contents will be stored
directly under \_source. If you need to keep JSon documents synchronized
to the index, set option `Add as Inner Object`_
which stores additional metadata and the JSon contents under field
``object``.

.. code:: yaml

   name: "test"
   fs:
     json_support: true

Of course, if you did not define a mapping before launching the crawler,
Elasticsearch will auto guess the mapping.

Indexing XML docs
^^^^^^^^^^^^^^^^^

.. versionadded:: 2.2

If you want to index XML files and convert them to JSON, you can set
``xml_support`` to ``true``. The content of XML files will be added
directly under \_source. If you need to keep XML documents synchronized
to the index, set option `Add as Inner Object`_
which stores additional metadata and the XML contents under field
``object``.

.. code:: json

   name: "test"
   fs:
     xml_support: true

Of course, if you did not define a mapping before launching the crawler,
Elasticsearch will auto guess the mapping.

Add as Inner Object
^^^^^^^^^^^^^^^^^^^

The default settings store the contents of json and xml documents
directly onto the \_source element of elasticsearch documents. Thereby,
there is no metadata about file and path settings, which are necessary
to determine if a document is deleted or updated. New files will however
be added to the index, (determined by the file timestamp).

If you need to keep json or xml documents synchronized to elasticsearch,
you should set this option.

.. code:: yaml

   name: "test"
   fs:
     add_as_inner_object: true

Index folders
^^^^^^^^^^^^^

.. versionadded:: 2.2

By default FSCrawler will index folder names in the folder index. If
you don’t want to index those folders, you can set ``index_folders`` to
``false``.

Note that in that case, FSCrawler won’t be able to detect removed
folders so any document has been indexed in elasticsearch, it won’t be
removed when you remove or move the folder away.

.. code:: yaml

   name: "test"
   fs:
     index_folders: false

Dealing with multiple types and multiple dirs
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

If you have more than one type, create as many crawlers as types:

``~/.fscrawler/test_type1/_settings.yaml``:

.. code:: yaml

   name: "test_type1"
   fs:
     url: "/tmp/type1"
     json_support: true
   elasticsearch:
     index: "mydocs1"
     index_folder: "myfolders1"

``~/.fscrawler/test_type2/_settings.yaml``:

.. code:: yaml

   name: "test_type2"
   fs:
     url: "/tmp/type2"
     json_support: true
   elasticsearch:
     index: "mydocs2"
     index_folder: "myfolders2"

``~/.fscrawler/test_type3/_settings.yaml``:

.. code:: yaml

   name: "test_type3"
   fs:
     url: "/tmp/type3"
     xml_support: true
   elasticsearch:
     index: "mydocs3"
     index_folder: "myfolders3"

Dealing with multiple types within the same dir
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

You can also index many types from one single dir using two crawlers
scanning the same dir and by setting ``includes`` parameter:

``~/.fscrawler/test_type1.yaml``:

.. code:: yaml

   name: "test_type1"
   fs:
     url: "/tmp"
     includes:
     - "type1*.json"
     json_support: true
   elasticsearch:
     index: "mydocs1"
     index_folder: "myfolders1"

``~/.fscrawler/test_type2.yaml``:

.. code:: yaml

   name: "test_type2"
   fs:
     url: "/tmp"
     includes:
     - "type2*.json"
     json_support: true
   elasticsearch:
     index: "mydocs2"
     index_folder: "myfolders2"

``~/.fscrawler/test_type3.yaml``:

.. code:: yaml

   name: "test_type3"
   fs:
     url: "/tmp"
     includes:
     - "*.xml"
     xml_support: true
   elasticsearch:
     index: "mydocs3"
     index_folder: "myfolders3"


.. _filename-as-id:

Using filename as elasticsearch ``_id``
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Please note that the document ``_id`` is always generated (hash value)
from the filename to avoid issues with special characters in filename.
You can force to use the ``_id`` to be the filename using
``filename_as_id`` attribute:

.. code:: yaml

   name: "test"
   fs:
     filename_as_id: true

Adding file attributes
^^^^^^^^^^^^^^^^^^^^^^

If you want to add file attributes such as ``attributes.owner``, ``attributes.group``
and ``attributes.permissions``, you can set ``attributes_support`` to ``true``.

.. code:: yaml

   name: "test"
   fs:
     attributes_support: true

.. note::

    On Windows systems, ``attributes.group`` and ``attributes.permissions`` are
    not generated.

Disabling raw metadata
^^^^^^^^^^^^^^^^^^^^^^

FSCrawler can extract all found metadata within a ``meta.raw`` object in addition
to the standard metadata fields.
If you want to enable this feature, you can set ``raw_metadata`` to ``true``.

.. code:: yaml

   name: "test"
   fs:
     raw_metadata: true

Generated raw metadata depends on the file format itself.

For example, a PDF document could generate:

.. code:: json

   {
      "date" : "2016-07-07T08:37:42Z",
      "pdf:PDFVersion" : "1.5",
      "xmp:CreatorTool" : "Microsoft Word",
      "Keywords" : "keyword1, keyword2",
      "access_permission:modify_annotations" : "true",
      "access_permission:can_print_degraded" : "true",
      "subject" : "Test Tika Object",
      "dc:creator" : "David Pilato",
      "dcterms:created" : "2016-07-07T08:37:42Z",
      "Last-Modified" : "2016-07-07T08:37:42Z",
      "dcterms:modified" : "2016-07-07T08:37:42Z",
      "dc:format" : "application/pdf; version=1.5",
      "title" : "Test Tika title",
      "Last-Save-Date" : "2016-07-07T08:37:42Z",
      "access_permission:fill_in_form" : "true",
      "meta:save-date" : "2016-07-07T08:37:42Z",
      "pdf:encrypted" : "false",
      "dc:title" : "Test Tika title",
      "modified" : "2016-07-07T08:37:42Z",
      "cp:subject" : "Test Tika Object",
      "Content-Type" : "application/pdf",
      "X-Parsed-By" : "org.apache.tika.parser.DefaultParser",
      "creator" : "David Pilato",
      "meta:author" : "David Pilato",
      "dc:subject" : "keyword1, keyword2",
      "meta:creation-date" : "2016-07-07T08:37:42Z",
      "created" : "Thu Jul 07 10:37:42 CEST 2016",
      "access_permission:extract_for_accessibility" : "true",
      "access_permission:assemble_document" : "true",
      "xmpTPg:NPages" : "2",
      "Creation-Date" : "2016-07-07T08:37:42Z",
      "access_permission:extract_content" : "true",
      "access_permission:can_print" : "true",
      "meta:keyword" : "keyword1, keyword2",
      "Author" : "David Pilato",
      "access_permission:can_modify" : "true"
   }

Where a MP3 file would generate:

.. code:: json

   {
      "xmpDM:genre" : "Vocal",
      "X-Parsed-By" : "org.apache.tika.parser.DefaultParser",
      "creator" : "David Pilato",
      "xmpDM:album" : "FS Crawler",
      "xmpDM:trackNumber" : "1",
      "xmpDM:releaseDate" : "2016",
      "meta:author" : "David Pilato",
      "xmpDM:artist" : "David Pilato",
      "dc:creator" : "David Pilato",
      "xmpDM:audioCompressor" : "MP3",
      "title" : "Test Tika",
      "xmpDM:audioChannelType" : "Stereo",
      "version" : "MPEG 3 Layer III Version 1",
      "xmpDM:logComment" : "Hello but reverted",
      "xmpDM:audioSampleRate" : "44100",
      "channels" : "2",
      "dc:title" : "Test Tika",
      "Author" : "David Pilato",
      "xmpDM:duration" : "1018.775146484375",
      "Content-Type" : "audio/mpeg",
      "samplerate" : "44100"
   }

.. note::
    All fields are generated as text even though they can be valid booleans or numbers.

    The ``meta.raw.*`` fields have a default mapping applied:

    .. code:: json

       {
         "type": "text",
         "fields": {
           "keyword": {
             "type": "keyword",
             "ignore_above": 256
           }
         }
       }

    If you want specifically tell elasticsearch to use a date type or a
    numeric type for some fields, you need to modify the default template
    provided by FSCrawler.

.. note::
    Note that dots in metadata names will be replaced by a ``:``. For
    example ``PTEX.Fullbanner`` will be indexed as ``PTEX:Fullbanner``.

.. note::
    Note that if you have a lot of different type of files, that can generate a lot of
    raw metadata which can make you hit the total number of field limit in elasticsearch
    mappings. In which case you will need to change the index settings ``foo``.

    See `elasticsearch documentation <https://www.elastic.co/guide/en/elasticsearch/reference/current/mapping.html#mapping-limit-settings>`__

Disabling file size field
^^^^^^^^^^^^^^^^^^^^^^^^^

By default, FSCrawler will create a field to store the original file
size in octets. You can disable it using \`add_filesize’ option:

.. code:: yaml

   name: "test"
   fs:
     add_filesize: false

Ignore deleted files
^^^^^^^^^^^^^^^^^^^^

If you don’t want to remove indexed documents when you remove a file or
a directory, you can set ``remove_deleted`` to ``false`` (default to
``true``):

.. code:: yaml

   name: "test"
   fs:
     remove_deleted: false

Ignore content
^^^^^^^^^^^^^^

If you don’t want to extract file content but only index filesystem
metadata such as filename, date, size and path, you can set
``index_content`` to ``false`` (default to ``true``):

.. code:: yaml

   name: "test"
   fs:
     index_content: false

.. _continue_on_error:

Continue on Error
^^^^^^^^^^^^^^^^^

.. versionadded:: 2.3

By default FSCrawler will immediately stop indexing if he hits a
Permission denied exception. If you want to just skip this File and
continue with the rest of the directory tree you can set
``continue_on_error`` to ``true`` (default to ``false``):

.. code:: yaml

   name: "test"
   fs:
     continue_on_error: true

Language detection
^^^^^^^^^^^^^^^^^^

.. versionadded:: 2.2

You can ask for language detection using ``lang_detect`` option:

.. code:: yaml

   name: "test"
   fs:
     lang_detect: true

In that case, a new field named ``meta.language`` is added to the
generated JSon document.

If you are using elasticsearch 5.0 or superior, you can use this value
to send your document to a specific index using a `Node Ingest
pipeline <#using-ingest-node-pipeline>`__.

For example, you can define a pipeline named ``langdetect`` with:

.. code:: sh

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

In FSCrawler settings, set both ``fs.lang_detect`` and
``elasticsearch.pipeline`` options:

.. code:: yaml

   name: "test"
   fs:
     lang_detect: true
   elasticsearch:
     pipeline: "langdetect"

And then, a document containing french text will be sent to
``myindex-fr``. A document containing english text will be sent to
``myindex-en``.

You can also imagine changing the field name from ``content`` to
``content-fr`` or ``content-en``. That will help you to define the
correct analyzer to use.

Language detection might detect more than one language in a given text
but only the most accurate will be set. Which means that if you have a
document containing 80% of french and 20% of english, the document will
be marked as ``fr``.

Note that language detection is CPU and time consuming.

.. _store_binary:

Storing binary source document
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

You can store in elasticsearch itself the binary document (BASE64 encoded)
using ``store_source`` option:

.. code:: yaml

   name: "test"
   fs:
     store_source: true

In that case, a new field named ``attachment`` is added to the generated
JSon document. This field is not indexed. Default mapping for
``attachment`` field is:

.. code:: json

   {
     "_doc" : {
       "properties" : {
         "attachment" : {
           "type" : "binary",
           "doc_values" : false
         }
         // ... Other properties here
       }
     }
   }

Extracted characters
^^^^^^^^^^^^^^^^^^^^

By default FSCrawler will extract only the first 100 000 characters.
But, you can set ``indexed_chars`` to ``5000`` in FSCrawler settings in
order to overwrite this default settings.

.. code:: yaml

   name: "test"
   fs:
     indexed_chars: "5000"

This number can be either a fixed size, number of characters that is, or
a percent using ``%`` sign. The percentage value will be applied to the
filesize to determine the number of character the crawler needs to
extract.

If you want to index only ``80%`` of filesize, define ``indexed_chars``
to ``"80%"``. Of course, if you want to index the full document, you can
set this property to ``"100%"``. Double values are also supported so
``"0.01%"`` is also a correct value.

**Compressed files**: If your file is compressed, you might need to
increase ``indexed_chars`` to more than ``"100%"``. For example,
``"150%"``.

If you want to extract the full content, define ``indexed_chars`` to
``"-1"``.

.. note::

    Tika requires to allocate in memory a data structure to
    extract text. Setting ``indexed_chars`` to a high number will require
    more memory!

Ignore Above
^^^^^^^^^^^^

.. versionadded:: 2.5

By default FSCrawler will send to Tika every single file, whatever its size.
But some files on your file system might be a way too big to be parsed.

Set ``ignore_above`` to the desired value of the limit.

.. code:: yaml

   name: "test"
   fs:
     ignore_above: "5mb"

File checksum
^^^^^^^^^^^^^

If you want FSCrawler to generate a checksum for each file, set
``checksum`` to the algorithm you wish to use to compute the checksum,
such as ``MD5`` or ``SHA-1``.

.. code:: yaml

   name: "test"
   fs:
     checksum: "MD5"

Follow Symlinks
^^^^^^^^^^^^^^^

.. versionadded:: 2.7

If you want FSCrawler to follow the symbolic links, you need to be explicit about it and set
``follow_symlink`` to ``true``. Starting from version 2.7, symbolic links are not followed anymore.

.. code:: yaml

   name: "test"
   fs:
     follow_symlink: true

Skip Tika
^^^^^^^^^^^^^^^

.. versionadded:: 2.7

If you want to skip Tika to parse your content you can. So contents will be indexed without getting parsed by tika.

.. code:: yaml

   name: "test"
   fs:
     skip_tika: true
