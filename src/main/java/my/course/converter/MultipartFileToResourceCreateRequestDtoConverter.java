package my.course.converter;

import my.course.dto.ResourceCreateRequestDto;
import my.course.exception.ResponseServiceException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.LyricsHandler;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Component
public class MultipartFileToResourceCreateRequestDtoConverter {
    public static final String
            TITLE = "dc:title",
            ARTIST = "xmpDM:artist",
            RELEASE_DATE = "xmpDM:releaseDate",
            DURATION = "xmpDM:duration",
            ALBUM = "xmpDM:album";

    public ResourceCreateRequestDto convert(MultipartFile file) {
        try {
            ResourceCreateRequestDto dto = new ResourceCreateRequestDto();

            //detecting the file type
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            InputStream inputStream = file.getInputStream();
            ParseContext pcontext = new ParseContext();
            //Mp3 parser
            Mp3Parser Mp3Parser = new Mp3Parser();
            Mp3Parser.parse(inputStream, handler, metadata, pcontext);
            LyricsHandler lyrics = new LyricsHandler(inputStream, handler);
            setAudioMetadata(dto, metadata);
            dto.setResource(getAudioData(file));
            return dto;
        } catch (Exception e) {
            throw ResponseServiceException.init500();
        }
    }

    private byte[] getAudioData(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            long length = file.getResource().contentLength();
            // initialize a byte array of size of the file
            byte[] fileContent = new byte[(int) length];
            // read the contents of file into byte array
            inputStream.read(fileContent);
            return fileContent;
        }
    }

    private void setAudioMetadata(ResourceCreateRequestDto dto, Metadata metadata) {
        String[] metadataNames = metadata.names();
        for (String name : metadataNames) {
            final String value = metadata.get(name);
            if (TITLE.equals(name)) {
                dto.getSongDto().setName(value);
            } else if (ARTIST.equals(name)) {
                dto.getSongDto().setArtist(value);
            } else if (ALBUM.equals(name)) {
                dto.getSongDto().setAlbum(value);
            } else if (DURATION.equals(name)) {
                dto.getSongDto().setLength(value);
            } else if (RELEASE_DATE.equals(name)) {
                dto.getSongDto().setYear(value);
            }
        }
    }
}
