// ============================================================================
//  File          : OptionalConstructorParameters
//  Created       : 29.08.2016   
//  Description   :
//  Modifications :
//
// ============================================================================
//  Copyright(c) 2016 XP Injection, Ukraine
// ============================================================================
package com.xpinjection.misuses.optional;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Alimenkou Mikalai
 * @version 1.0
 */
public class OptionalConstructorParameters {
    public class Misuse {
        public List<Email> create() {
            Email noAttachment = new Email("First!", "No attachment", Optional.<Attachment>empty());
            Attachment attachment = new Attachment("/mnt/files/image.png", 370);
            Email withAttachment = new Email("Second!", "With attachment", Optional.of(attachment));
            return Arrays.asList(noAttachment, withAttachment);
        }

        public class Email implements Serializable {
            private final String subject;
            private final String body;
            private final Optional<Attachment> attachment;

            public Email(String subject, String body, Optional<Attachment> attachment) {
                this.subject = subject;
                this.body = body;
                this.attachment = attachment;
            }

            public String getSubject() {
                return subject;
            }

            public String getBody() {
                return body;
            }

            public Optional<Attachment> getAttachment() {
                return attachment;
            }
        }
    }

    public class Correct {
        public List<Email> create() {
            Email noAttachment = new Email("First!", "No attachment");
            Attachment attachment = new Attachment("/mnt/files/image.png", 370);
            Email withAttachment = new Email("Second!", "With attachment", attachment);
            return Arrays.asList(noAttachment, withAttachment);
        }

        public class Email implements Serializable {
            private final String subject;
            private final String body;
            private final Attachment attachment;

            public Email(String subject, String body, Attachment attachment) {
                this.subject = subject;
                this.body = body;
                this.attachment = attachment;
            }

            public Email(String subject, String body) {
                this(subject, body, null);
            }

            public String getSubject() {
                return subject;
            }

            public String getBody() {
                return body;
            }

            public boolean hasAttachment() {
                return attachment != null;
            }

            public Attachment getAttachment() {
                return attachment;
            }
        }
    }

    public class Attachment {
        private final String path;
        private final int size;

        public Attachment(String path, int size) {
            this.path = path;
            this.size = size;
        }

        public String getPath() {
            return path;
        }

        public int getSize() {
            return size;
        }
    }
}
